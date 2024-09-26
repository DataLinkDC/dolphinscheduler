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

package org.apache.dolphinscheduler.api.validator.resource;

import org.apache.dolphinscheduler.api.dto.resources.FetchFileContentDto;
import org.apache.dolphinscheduler.api.exceptions.ServiceException;
import org.apache.dolphinscheduler.dao.entity.User;
import org.apache.dolphinscheduler.dao.repository.TenantDao;
import org.apache.dolphinscheduler.plugin.storage.api.StorageOperator;

import org.springframework.stereotype.Component;

@Component
public class FetchFileContentDtoValidator extends AbstractResourceValidator<FetchFileContentDto> {

    public FetchFileContentDtoValidator(StorageOperator storageOperator, TenantDao tenantDao) {
        super(storageOperator, tenantDao);
    }

    @Override
    public void validate(FetchFileContentDto fetchFileContentDto) {
        if (fetchFileContentDto.getSkipLineNum() < 0) {
            throw new ServiceException("skipLineNum must be greater than or equal to 0");
        }
        String resourceFileAbsolutePath = fetchFileContentDto.getResourceFileAbsolutePath();
        User loginUser = fetchFileContentDto.getLoginUser();

        exceptionResourceAbsolutePathInvalidated(resourceFileAbsolutePath);
        exceptionResourceIsNotFile(resourceFileAbsolutePath);
        exceptionUserNoResourcePermission(loginUser, resourceFileAbsolutePath);
        exceptionFileContentCannotFetch(resourceFileAbsolutePath);
    }
}
