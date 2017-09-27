package com.sso.common.server.dto.idm;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sso.common.server.dto.GenericEntityDto;

/**
 * @author Original Author: Alexander Kontarero
 * @version 2/2/2015
 * @see © 2015 SSO - All Rights Reserved
 * See LICENSE file for further details
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class UserRoleDto implements GenericEntityDto<Long> {
    private Long id;
   	private Long userId;
   	private Long roleId;

    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
