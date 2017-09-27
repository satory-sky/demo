package com.sso.idm.server.mappers;

import com.sso.common.server.dto.idm.RoleDto;
import com.sso.common.server.mappers.GenericMapper;
import com.sso.common.server.model.entities.Role;
import org.springframework.stereotype.Component;

/**
 * @author Original Author: Alexander Kontarero
 * @version 1/28/2015
 * @see © 2015 SSO - All Rights Reserved
 * See LICENSE file for further details
 */
@Component
public class RoleMapper extends GenericMapper<RoleDto, Role> {
    public RoleMapper() {
           super(RoleDto.class, Role.class);
       }
}
