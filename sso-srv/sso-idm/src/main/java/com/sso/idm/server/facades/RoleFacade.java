package com.sso.idm.server.facades;

import com.sso.common.server.dto.ResponsePackageDto;
import com.sso.common.server.dto.idm.RoleDto;
import com.sso.common.server.facades.GenericFacade;

import java.util.List;

/**
 * @author Original Author: Alexander Kontarero
 * @version 1/28/2015
 * @see © 2015 SSO - All Rights Reserved
 * See LICENSE file for further details
 */
public interface RoleFacade extends GenericFacade<RoleDto, Long> {
    ResponsePackageDto assignRoles(List<Long> roleIds, List<Long> userIds, Boolean isDeleteAssigned);
}
