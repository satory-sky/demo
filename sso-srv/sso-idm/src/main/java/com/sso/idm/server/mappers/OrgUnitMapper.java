package com.sso.idm.server.mappers;

import com.sso.common.server.dto.idm.OrgUnitDto;
import com.sso.common.server.mappers.GenericMapper;
import com.sso.common.server.model.entities.OrgUnit;
import org.springframework.stereotype.Component;

/**
 * @author Original Author: Alexander Kontarero
 * @version 1/28/2015
 * @see © 2015 SSO - All Rights Reserved
 * See LICENSE file for further details
 */
@Component
public class OrgUnitMapper extends GenericMapper<OrgUnitDto, OrgUnit> {
    public OrgUnitMapper() {
        super(OrgUnitDto.class, OrgUnit.class);
    }
}