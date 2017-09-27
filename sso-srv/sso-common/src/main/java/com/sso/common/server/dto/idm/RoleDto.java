package com.sso.common.server.dto.idm;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sso.common.server.dto.GenericEntityDto;

/**
 * @author Original Author: Alexander Kontarero
 * @version 1/28/2015
 * @see © 2015 SSO - All Rights Reserved
 * See LICENSE file for further details
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class RoleDto implements GenericEntityDto<Long> {
    private Long id;
    private String name;
    private String description;

    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
