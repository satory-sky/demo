package com.sso.common.server.dto.idm;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sso.common.server.dto.GenericEntityDto;

import java.sql.Timestamp;

/**
 * @author Original Author: Alexander Kontarero
 * @version 1/28/2015
 * @see © 2015 SSO - All Rights Reserved
 * See LICENSE file for further details
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class OrgUnitDto implements GenericEntityDto<Long> {
    private Long id;
    private String name;
    private String abbreviation;
    private String orgUnitType;
    private Long parentId;
    private String code;
    private Timestamp created;
    private Timestamp updated;
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

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getOrgUnitType() {
        return orgUnitType;
    }

    public void setOrgUnitType(String orgUnitType) {
        this.orgUnitType = orgUnitType;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Timestamp getUpdated() {
        return updated;
    }

    public void setUpdated(Timestamp updated) {
        this.updated = updated;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
