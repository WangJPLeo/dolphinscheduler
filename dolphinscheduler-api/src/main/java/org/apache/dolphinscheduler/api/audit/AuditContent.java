package org.apache.dolphinscheduler.api.audit;

import java.util.Date;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AuditContent {

    private Integer userId;

    private String auditResourceType;

    private String auditOperationType;

    private String requestPath;

    private String requestMethod;

    private Long currentTimeMills;

    private Map<String, Object> allArgs;

    private Object responseData;

    private String originalDataSnapshot;

    private String executeTimeMills;

    private String classMethodLocation;

    private String remoteIp;

    private Date nowTime;

    private String responseType;

    private String language;

    private String returnType;
}
