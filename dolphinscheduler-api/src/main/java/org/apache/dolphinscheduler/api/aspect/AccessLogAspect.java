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

package org.apache.dolphinscheduler.api.aspect;

import org.apache.dolphinscheduler.api.audit.AuditContent;
import org.apache.dolphinscheduler.api.audit.AuditPublishService;
import org.apache.dolphinscheduler.api.constants.AuditOperationTypeConstant;
import org.apache.dolphinscheduler.api.service.SessionService;
import org.apache.dolphinscheduler.common.Constants;
import org.apache.dolphinscheduler.dao.entity.Session;
import org.apache.dolphinscheduler.dao.entity.User;
import org.apache.dolphinscheduler.dao.mapper.UserMapper;
import org.apache.dolphinscheduler.spi.utils.StringUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class AccessLogAspect {
    private static final Logger logger = LoggerFactory.getLogger(AccessLogAspect.class);

    @Autowired
    private AuditPublishService auditPublishService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private UserMapper userMapper;

    private static final String TRACE_ID = "traceId";

    public static final String sensitiveDataRegEx = "(password=[\'\"]+)(\\S+)([\'\"]+)";

    private static final Pattern sensitiveDataPattern = Pattern.compile(sensitiveDataRegEx, Pattern.CASE_INSENSITIVE);

    public static final String PARAM_HIDE = "userPassword,newPassword,request,response,file";

    private static final String DOLPHIN_SCHEDULER_ROOT_PATH = "/dolphinscheduler";

    @Pointcut("@annotation(org.apache.dolphinscheduler.api.aspect.AccessLogAnnotation)")
    public void logPointCut(){
        // Do nothing because of it's a pointcut
    }

    @Around("logPointCut()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        HttpServletRequest httpServletRequest = servletRequest();
        Integer userId = userId(httpServletRequest);

        Object ob = null;
        try {
            ob = proceedingJoinPoint.proceed();
        } finally {
            afterProceed(ob, proceedingJoinPoint, httpServletRequest, userId, startTime);
        }
        return ob;
    }

    private HttpServletRequest servletRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            logger.error("get requestAttributes is empty.");
            return null;
        }
        return requestAttributes.getRequest();
    }

    private Object afterProceed(Object proceed, ProceedingJoinPoint proceedingJoinPoint, HttpServletRequest request,
                                Integer userId, long startTime) {
        try {
            // fetch AccessLogAnnotation
            MethodSignature methodSignature =  (MethodSignature) proceedingJoinPoint.getSignature();
            Method method = methodSignature.getMethod();
            AccessLogAnnotation annotation = method.getAnnotation(AccessLogAnnotation.class);

            // filter
            if (annotation == null || annotation.ignoreRequest()) {
                return proceed;
            }
            String operationType = annotation.operationType();
            if (operationType.equals(AuditOperationTypeConstant.READ) && annotation.readOperationFilter()) {
                return proceed;
            }
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (requestAttributes == null) {
                logger.error("get requestAttributes is empty.");
                return proceed;
            }
            String returnType = methodSignature.getMethod().getReturnType().getName();
            Map<String, Object> argsMap = parseArgsMap(proceedingJoinPoint, annotation);
            String localClassMethod = methodSignature.getName();

            String requestUrl = request.getRequestURI().replace(DOLPHIN_SCHEDULER_ROOT_PATH, Constants.EMPTY_STRING);

            AuditContent auditContent = AuditContent.builder()
                    .userId(userId)
                    .auditResourceType(annotation.logMoudle())
                    .auditOperationType(operationType)
                    .requestPath(requestUrl)
                    .requestMethod(request.getMethod())
                    .currentTimeMills(startTime)
                    .allArgs(argsMap)
                    .responseData(proceed)
                    .executeTimeMills((System.currentTimeMillis() - startTime) + "ms")
                    .classMethodLocation(localClassMethod)
                    .remoteIp(request.getRemoteAddr())
                    .nowTime(new Date())
                    .responseType(methodSignature.getReturnType().getName())
                    .language(LocaleContextHolder.getLocale().getLanguage())
                    .returnType(returnType)
                    .build();

            // async thrown into the queue
            auditPublishService.publish(auditContent);
            return proceed;
        } catch (Exception e) {
            logger.error("audit log point cut error", e);
        }
        return proceed;
    }

    private Integer userId(HttpServletRequest request) {
        try {
            if (request == null) {
                return null;
            }
            Session session = sessionService.getSession(request);
            if (Objects.nonNull(session)) {
                return session.getUserId();
            }
            String token = request.getHeader(Constants.TOKEN);
            if (StringUtils.isEmpty(token)) {
                return null;
            }
            User user = userMapper.queryUserByToken(token, new Date());
            return user.getId();
        } catch (Exception e) {
            logger.error("aspect log user query error", e);
        }
        return null;
    }

    private String parseArgs(ProceedingJoinPoint proceedingJoinPoint, AccessLogAnnotation annotation) {
        Object[] args = proceedingJoinPoint.getArgs();
        String argsString = Arrays.toString(args);
        if (annotation.ignoreRequestArgs().length > 0) {
            String[] parameterNames = ((MethodSignature) proceedingJoinPoint.getSignature()).getParameterNames();
            if (parameterNames.length > 0) {
                Set<String> ignoreSet = Arrays.stream(annotation.ignoreRequestArgs()).collect(Collectors.toSet());
                HashMap<String, Object> argsMap = new HashMap<>();

                for (int i = 0; i < parameterNames.length; i++) {
                    if (!ignoreSet.contains(parameterNames[i])) {
                        argsMap.put(parameterNames[i], args[i]);
                    }
                }
                argsString = argsMap.toString();
            }
        }
        return argsString;
    }

    private Map<String, Object> parseArgsMap(ProceedingJoinPoint proceedingJoinPoint, AccessLogAnnotation annotation) {
        Object[] args = proceedingJoinPoint.getArgs();
        HashMap<String, Object> argsMap = new HashMap<>(args.length);
        if (annotation.ignoreRequestArgs().length <= 0) {
            return argsMap;
        }
        String[] parameterNames = ((MethodSignature) proceedingJoinPoint.getSignature()).getParameterNames();
        if (parameterNames.length <= 0) {
            return argsMap;
        }
        Set<String> ignoreSet = Arrays.stream(annotation.ignoreRequestArgs()).collect(Collectors.toSet());
        for (int i = 0; i < parameterNames.length; i++) {
            if (!ignoreSet.contains(parameterNames[i])) {
                String parameterName = parameterNames[i];
                argsMap.put(parameterName, PARAM_HIDE.contains(parameterName) ? null : args[i]);
            }
        }
        return argsMap;
    }

    protected String handleSensitiveData(String originalData) {
        Matcher matcher = sensitiveDataPattern.matcher(originalData.toLowerCase());
        IntStream stream = IntStream.builder().build();
        boolean exists = false;
        while (matcher.find()) {
            if (matcher.groupCount() == 3) {
                stream = IntStream.concat(stream, IntStream.range(matcher.end(1),matcher.end(2)));
                exists = true;
            }
        }

        if (exists) {
            char[] chars = originalData.toCharArray();
            stream.forEach(idx -> {
                chars[idx] = '*';
            });
            return new String(chars);
        }

        return originalData;
    }

    private String parseLoginInfo(HttpServletRequest request) {
        String userName = "NOT LOGIN";
        User loginUser = (User) (request.getAttribute(Constants.SESSION_USER));
        if (loginUser != null) {
            userName = loginUser.getUserName();
        }
        return userName;
    }

}
