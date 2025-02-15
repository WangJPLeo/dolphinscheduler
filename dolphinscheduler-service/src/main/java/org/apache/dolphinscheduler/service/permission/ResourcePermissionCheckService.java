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

package org.apache.dolphinscheduler.service.permission;


import org.apache.dolphinscheduler.common.enums.AuthorizationType;
import org.slf4j.Logger;

import java.util.Set;

public interface ResourcePermissionCheckService<T> {

    /**
     * resourcePermissionCheck
     * @param authorizationType
     * @param needChecks
     * @param userId
     * @param logger
     * @return
     */
    boolean resourcePermissionCheck(AuthorizationType authorizationType, T[] needChecks, int userId, Logger logger);

    /**
     * userOwnedResourceIdsAcquisition
     * @param authorizationType
     * @param userId
     * @param logger
     * @param <T>
     * @return
     */
    <T> Set<T> userOwnedResourceIdsAcquisition(AuthorizationType authorizationType, int userId, Logger logger);

    /**
     * operationpermissionCheck
     * @param authorizationType
     * @param userId
     * @param sourceUrl
     * @param logger
     * @return
     */
    boolean operationPermissionCheck(AuthorizationType authorizationType, int userId, String sourceUrl, Logger logger);
}
