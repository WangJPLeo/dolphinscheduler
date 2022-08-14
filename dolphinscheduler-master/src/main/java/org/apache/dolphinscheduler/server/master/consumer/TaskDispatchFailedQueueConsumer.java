package org.apache.dolphinscheduler.server.master.consumer;

import org.apache.dolphinscheduler.common.Constants;
import org.apache.dolphinscheduler.common.lifecycle.ServerLifeCycleManager;
import org.apache.dolphinscheduler.common.thread.BaseDaemonThread;
import org.apache.dolphinscheduler.common.thread.ThreadUtils;
import org.apache.dolphinscheduler.server.master.config.MasterConfig;
import org.apache.dolphinscheduler.server.master.metrics.TaskMetrics;
import org.apache.dolphinscheduler.service.exceptions.TaskPriorityQueueException;
import org.apache.dolphinscheduler.service.queue.TaskPriority;
import org.apache.dolphinscheduler.service.queue.TaskPriorityQueue;

import java.util.Objects;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class TaskDispatchFailedQueueConsumer extends BaseDaemonThread {

    private static final Logger logger = LoggerFactory.getLogger(TaskDispatchFailedQueueConsumer.class);

    /**
     * taskPriorityQueue
     */
    @Qualifier(Constants.TASK_PRIORITY_QUEUE)
    private TaskPriorityQueue<TaskPriority> taskPriorityQueue;

    /**
     * taskDispatchFailedQueue
     */
    @Qualifier(Constants.TASK_DISPATCH_FAILED_QUEUE)
    private TaskPriorityQueue<TaskPriority> taskDispatchFailedQueue;

    @Autowired
    private MasterConfig masterConfig;

    private ThreadPoolExecutor retryConsumerThreadPoolExecutor;

    /**
     * delay time for retries
     */
    private static final Long[] TIME_DELAY;

    /**
     * initialization failure retry delay rule
     */
    static {
        TIME_DELAY = new Long[Constants.DEFAULT_MAX_RETRY_COUNT];
        for (int i = 0; i < Constants.DEFAULT_MAX_RETRY_COUNT; i++) {
            int delayTime = (i + 1) * 1000;
            TIME_DELAY[i] = (long) delayTime;
        }
    }

    protected TaskDispatchFailedQueueConsumer(String threadName) {
        super("TaskDispatchFailedQueueConsumerThread");
    }

    @PostConstruct
    public void init() {
        this.retryConsumerThreadPoolExecutor = (ThreadPoolExecutor) ThreadUtils
                .newDaemonFixedThreadExecutor("TaskDispatchFailedQueueConsumerThread", masterConfig.getDispatchTaskNumber());
        super.start();
    }

    @Override
    public void run() {
        while (!ServerLifeCycleManager.isStopped()) {
            try {
                failedRetry();
            } catch (Exception e) {
                TaskMetrics.incTaskDispatchError();
                logger.error("failed task retry error", e);
            }
        }
    }

    public void failedRetry() throws TaskPriorityQueueException {
        if (taskDispatchFailedQueue.size() > 0) {
            retryConsumerThreadPoolExecutor.submit(() -> dispatchFailedBackToTaskPriorityQueue(masterConfig.getDispatchTaskNumber()));
        }
    }

    /**
     * put the failed dispatch task into the dispatch queue again
     */
    private void dispatchFailedBackToTaskPriorityQueue(int fetchTaskNum) {
        for (int i = 0; i < fetchTaskNum; i++) {
            try {
                TaskPriority dispatchFailedTaskPriority = taskDispatchFailedQueue.poll(Constants.SLEEP_TIME_MILLIS, TimeUnit.MILLISECONDS);
                if (Objects.isNull(dispatchFailedTaskPriority)) {
                    continue;
                }
                if (canRetry(dispatchFailedTaskPriority)) {
                    dispatchFailedTaskPriority.setDispatchFailedRetryTimes(dispatchFailedTaskPriority.getDispatchFailedRetryTimes() + 1);
                    taskPriorityQueue.put(dispatchFailedTaskPriority);
                } else {
                    taskDispatchFailedQueue.put(dispatchFailedTaskPriority);
                }
            } catch (InterruptedException exception) {
                logger.error("dispatch failed queue poll error", exception);
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                logger.error("dispatch failed back to task priority queue error", e);
            }
        }
    }

    /**
     * the time interval is adjusted according to the number of retries
     */
    private boolean canRetry (TaskPriority taskPriority){
        int dispatchFailedRetryTimes = taskPriority.getDispatchFailedRetryTimes();
        long now = System.currentTimeMillis();
        // retry more than 100 times with 100 seconds delay each time
        if (dispatchFailedRetryTimes >= Constants.DEFAULT_MAX_RETRY_COUNT){
            return now - taskPriority.getLastDispatchTime() >= TIME_DELAY[Constants.DEFAULT_MAX_RETRY_COUNT - 1];
        }
        return now - taskPriority.getLastDispatchTime() >= TIME_DELAY[dispatchFailedRetryTimes];
    }
}
