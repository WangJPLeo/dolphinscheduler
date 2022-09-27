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

package org.apache.dolphinscheduler.api.audit;

import org.apache.dolphinscheduler.common.Constants;
import org.apache.dolphinscheduler.common.enums.AuditOperationType;
import org.apache.dolphinscheduler.common.enums.AuditResourceType;
import org.apache.dolphinscheduler.common.utils.JSONUtils;
import org.apache.dolphinscheduler.dao.entity.AuditLog;
import org.apache.dolphinscheduler.dao.entity.User;
import org.apache.dolphinscheduler.dao.mapper.AuditLogMapper;
import org.apache.dolphinscheduler.dao.mapper.UserMapper;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuditSubscriberImpl implements AuditSubscriber {

    @Autowired
    private AuditLogMapper auditLogMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public void execute(AuditContent content) {
        AuditLog auditLog = new AuditLog();
        Integer userId = buildUserId(content);
        // operation by unknown user
        auditLog.setUserId(userId == null ? 0 : userId);
        auditLog.setResourceType(AuditResourceType.valueOf(content.getAuditResourceType()).getCode());
        auditLog.setOperation(AuditOperationType.valueOf(content.getAuditOperationType()).getCode());
        auditLog.setResourceId(null);
        auditLog.setAroundDetail(JSONUtils.toJsonString(content));
        auditLog.setSnapshotBeforeOperation(content.getOriginalDataSnapshot());
        auditLog.setDetailsAfterOperation(detailsAfterOperation(content));
        auditLog.setTime(content.getNowTime());
        auditLogMapper.insert(auditLog);
    }

    private String detailsAfterOperation(AuditContent content) {
        return null;
    }

    private Integer buildUserId(AuditContent content) {
        Integer userId = content.getUserId();
        Map<String, Object> allArgs = content.getAllArgs();

        if (content.getClassMethodLocation().equals(Constants.LOGIN_PATH)) {
            User user = userMapper.queryByUserNameAccurately((String) allArgs.get(Constants.LOGIN_USER_NAME));
            if (userId == null && user == null) {
                return null;
            }
            return user.getId();
        }
        return userId;
    }
}
