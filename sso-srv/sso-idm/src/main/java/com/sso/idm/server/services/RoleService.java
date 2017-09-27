package com.sso.idm.server.services;


import com.sso.common.server.model.entities.Role;
import com.sso.common.server.model.entities.UserRole;
import com.sso.common.server.services.GenericService;

import java.util.List;

/**
 * @author Original Author: Alexander Kontarero
 * @version 11/11/2014
 * @see © 2014 SSO - All Rights Reserved
 * See LICENSE file for further details
 */
public interface RoleService extends GenericService<Long, Role> {
    List<Role> getByUserId(Long id);

    List<UserRole> assignRoles(List<Long> roleIds, List<Long> userIds, Boolean isDeleteAssigned);
}
