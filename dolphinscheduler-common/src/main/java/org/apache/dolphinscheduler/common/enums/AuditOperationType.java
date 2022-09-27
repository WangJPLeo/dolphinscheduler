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

import java.util.HashMap;

/**
 * Audit Operation type
 */
public enum AuditOperationType {

    CREATE(0, "CREATE"),
    READ(1, "READ"),
    UPDATE(2, "UPDATE"),
    DELETE(3, "DELETE"),
    LOGIN(4, "LOGIN"),
    SIGNOUT(5, "SIGNOUT"),
    CONNECT(6, "CONNECT"),
    AUTH(7, "UNAUTH"),
    UNAUTH(8, "UNAUTH"),
    EXECUTE(9, "EXECUTE"),
    COPY(10, "COPY"),
    MOVE(11, "MOVE"),
    SWITCH(12, "SWITCH"),
    OFFLINE(13, "OFFLINE"),
    CLOSE(14, "CLOSE"),
    FORCE_SUCCESS(15, "FORCE_SUCCESS"),
    STOP(16, "STOP"),
    RELEASE(17, "RELEASE"),
    DOWNLOAD(18, "DOWNLOAD"),
    UPLOAD(19, "UPLOAD"),
    RELATION(20, "RELATION"),
    UNRELATION(21, "UNRELATION"),

    OTHER(999, "OTHER"),
    ;

    private final int code;
    private final String enMsg;

    private static HashMap<Integer, AuditOperationType> AUDIT_OPERATION_MAP = new HashMap<>();

    static {
        for (AuditOperationType operationType : AuditOperationType.values()) {
            AUDIT_OPERATION_MAP.put(operationType.code, operationType);
        }
    }

    AuditOperationType(int code, String enMsg) {
        this.code = code;
        this.enMsg = enMsg;
    }

    public static AuditOperationType of(int status) {
        if (AUDIT_OPERATION_MAP.containsKey(status)) {
            return AUDIT_OPERATION_MAP.get(status);
        }
        throw new IllegalArgumentException("invalid audit operation type code " + status);
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return enMsg;
    }
}
