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

package org.apache.dolphinscheduler.common.enums;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Audit Module type
 */
public enum AuditResourceType {
    // TODO: add other audit resource enums
    TENANT(0, "TENANT"),
    USER(1, "USER"),
    ALERT_GROUP(2, "ALERT_GROUP"),
    ALERT_PLUGIN_INSTANCE(3, "ALERT_PLUGIN_INSTANCE"),
    WORKER_GROUP(4, "WORKER_GROUP"),
    YARN_QUEUE(5, "YARN_QUEUE"),
    ENVIRONMENT(6, "ENVIRONMENT"),
    CLUSTER(7, "CLUSTER"),
    K8S_NAMESPACE(8, "K8S_NAMESPACE"),
    ACCESS_TOKEN(9, "ACCESS_TOKEN"),

    DATASOURCE(10, "DATASOURCE"),

    RESOURCE(11, "RESOURCE"),
    UDF_FUNC(12, "UDF_FUNC"),
    TASK_GROUP(13, "TASK_GROUP"),
    TASK_GROUP_QUEUE(14, "TASK_GROUP_QUEUE"),

    PROJECT(15, "PROJECT"),
    PROCESS_DEFINITION(16, "PROCESS_DEFINITION"),
    PROCESS_INSTANCE(17, "PROCESS_INSTANCE"),
    TASK_DEFINITION(18, "TASK_DEFINITION"),
    TASK_INSTANCE(19, "TASK_INSTANCE"),

    ACCESS(20, "ACCESS"),
    AUDIT_LOG(21, "AUDIT_LOG"),
    DATA_ANALYSIS(22, "DATA_ANALYSIS"),
    DATA_QUALITY(23, "DATA_QUALITY"),
    FAVOURITE(24, "FAVOURITE"),
    LOGGER(25, "LOGGER"),
    MONITOR(26, "MONITOR"),
    SCHEDULE(27, "SCHEDULE"),

    UI_PLUGIN(28, "UI_PLUGIN"),
    WORKFLOW_LINEAGE(29, "WORKFLOW_LINEAGE"),

    OTHER(999, "OTHER"),
    ;

    private final int code;
    private final String enMsg;

    private static HashMap<Integer, AuditResourceType> AUDIT_RESOURCE_MAP = new HashMap<>();

    static {
        for (AuditResourceType auditResourceType : AuditResourceType.values()) {
            AUDIT_RESOURCE_MAP.put(auditResourceType.code, auditResourceType);
        }
    }

    public static void main(String[] args) {
        String format = "public static final String %s = \"%s\";";
        Arrays.stream(AuditResourceType.values()).forEach(a -> {
            System.out.println(String.format(format, a.name(), a.name()));
            System.out.println();
        });
    }

    AuditResourceType(int code, String enMsg) {
        this.code = code;
        this.enMsg = enMsg;
    }

    public int getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.enMsg;
    }

    public static AuditResourceType of(int status) {
        if (AUDIT_RESOURCE_MAP.containsKey(status)) {
            return AUDIT_RESOURCE_MAP.get(status);
        }
        throw new IllegalArgumentException("invalid audit resource type code " + status);
    }
}
