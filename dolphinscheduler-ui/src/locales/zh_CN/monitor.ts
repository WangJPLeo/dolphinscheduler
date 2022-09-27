/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

export default {
  master: {
    cpu_usage: '处理器使用量',
    memory_usage: '内存使用量',
    disk_available: '磁盘可用容量',
    load_average: '平均负载量',
    create_time: '创建时间',
    last_heartbeat_time: '最后心跳时间',
    directory_detail: '目录详情',
    host: '主机',
    directory: '注册目录',
    master_no_data_result_title: 'Master节点不存在',
    master_no_data_result_desc:
      '目前没有任何Master节点，请先创建Master节点，再访问该页面'
  },
  worker: {
    cpu_usage: '处理器使用量',
    memory_usage: '内存使用量',
    disk_available: '磁盘可用容量',
    load_average: '平均负载量',
    create_time: '创建时间',
    last_heartbeat_time: '最后心跳时间',
    directory_detail: '目录详情',
    host: '主机',
    directory: '注册目录',
    worker_no_data_result_title: 'Worker节点不存在',
    worker_no_data_result_desc:
      '目前没有任何Worker节点，请先创建Worker节点，再访问该页面'
  },
  db: {
    health_state: '健康状态',
    max_connections: '最大连接数',
    threads_connections: '当前连接数',
    threads_running_connections: '数据库当前活跃连接数',
    db_no_data_result_title: 'DB节点不存在',
    db_no_data_result_desc: '目前没有任何DB节点，请先创建DB节点，再访问该页面'
  },
  statistics: {
    command_number_of_waiting_for_running: '待执行的命令数',
    failure_command_number: '执行失败的命令数'
  },
  audit_log: {
    user_name: '用户名称',
    resource_type: '资源类型',
    resource_name: '资源名称',
    operation_type: '操作类型',
    create_time: '创建时间',
    start_time: '开始时间',
    end_time: '结束时间',
    tenant_audit: '租户管理审计',
    user_audit: '用户管理审计',
    alert_group_audit: '告警组管理审计',
    alert_plugin_instance_audit: '告警实例管理审计',
    worker_group_audit: 'Worker分组管理审计',
    yarn_queue_audit: 'Yarn队列管理审计',
    env_audit: '环境管理审计',
    cluster_audit: '集群管理审计',
    k8s_audit: 'K8S命名空间管理审计',
    token_audit: '令牌管理审计',
    datasource_audit: '数据源管理审计',
    resource_audit: '文件管理审计',
    udf_audit: 'UDF管理审计',
    task_group_audit: '任务组管理审计',
    task_group_queue_audit: '任务组队列管理审计',
    project_audit: '项目管理审计',
    process_definition_audit: '工作流定义管理审计',
    process_instance_audit: '工作流实例管理审计',
    task_definition_audit: '任务定义管理审计',
    task_instance_audit: '任务实例管理审计',
    access_audit: '访问管理审计',
    audit_log_audit: '审计日志管理审计',
    data_analysis_audit: '数据分析管理审计',
    data_quality_audit: '数据质量管理审计',
    favourite_audit: '标记管理审计',
    logger_audit: '日志管理审计',
    monitor_audit: '监控管理审计',
    schedule_audit: '定时管理审计',
    ui_plugin_audit: 'UI插件管理审计',
    workflow_lineage_audit: '工作流血缘管理审计',
    other_audit: '其他管理审计',
    create: '创建',
    update: '更新',
    delete: '删除',
    read: '读取',
    login: '登录',
    sign_out: '登出',
    connect: '连接',
    auth: '授权',
    un_auth: '取消授权',
    execute: '执行',
    copy: '复制',
    move: '移动',
    switch: '切换',
    offline: '下线',
    close: '关闭',
    force_success: '强制成功',
    stop: '停止',
    release: '发布',
    download: '下载',
    upload: '上传',
    relation: '关联',
    un_relation: '取消关联',
    other: '其他'
  }
}
