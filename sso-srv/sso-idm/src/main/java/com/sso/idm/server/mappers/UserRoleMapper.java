package com.sso.idm.server.mappers;

import com.sso.common.server.dto.idm.UserRoleDto;
import com.sso.common.server.mappers.GenericMapper;
import com.sso.common.server.model.entities.UserRole;
import org.springframework.stereotype.Component;

/**
 * @author Original Author: Alexander Kontarero
 * @version 2/2/2015
 * @see © 2015 SSO - All Rights Reserved
 * See LICENSE file for further details
 */
@Component
public class UserRoleMapper extends GenericMapper<UserRoleDto, UserRole> {
    public UserRoleMapper() {
            super(UserRoleDto.class, UserRole.class);
        }
}
