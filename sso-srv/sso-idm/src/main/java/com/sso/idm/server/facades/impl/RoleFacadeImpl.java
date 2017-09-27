package com.sso.idm.server.facades.impl;

import com.sso.common.server.dto.ResponsePackageDto;
import com.sso.common.server.dto.idm.RoleDto;
import com.sso.common.server.facades.impl.GenericFacadeImpl;
import com.sso.common.server.mappers.GenericMapper;
import com.sso.common.server.model.entities.Role;
import com.sso.common.server.model.entities.UserRole;
import com.sso.common.server.services.GenericService;
import com.sso.idm.server.facades.RoleFacade;
import com.sso.idm.server.mappers.RoleMapper;
import com.sso.idm.server.mappers.UserRoleMapper;
import com.sso.idm.server.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Original Author: Alexander Kontarero
 * @version 1/28/2015
 * @see © 2015 SSO - All Rights Reserved
 * See LICENSE file for further details
 */
@Service
public class RoleFacadeImpl extends GenericFacadeImpl<RoleDto, Long, Role> implements RoleFacade {
    @Autowired
    private RoleService roleService;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

    private static final String REQUEST_HEADER_USER_ID = "userId";

    public RoleFacadeImpl() {
        super(Long.class);
    }

    @Override
    public GenericService<Long, Role> getService() {
        log.debug(">>getService");
        return roleService;
    }

    @Override
    public GenericMapper<RoleDto, Role> getMapper() {
        log.debug(">>getMapper");
        return roleMapper;
    }

    @Override
    public ResponsePackageDto getListByParams(Map<String, String> parameters) {
        log.debug(">>getListByParams {}", parameters);

        ResponsePackageDto responsePackageDto = null;
        Long userId = getExtractedParameter(parameters, REQUEST_HEADER_USER_ID, Long.class);
        if (userId != null) {
            List<Role> roles = roleService.getByUserId(userId);
            responsePackageDto = new ResponsePackageDto();
            responsePackageDto.setResult(getMapper().getDtos(roles));
        }

        log.debug("<<getListByParams");
        return responsePackageDto;
    }

    @Override
    public ResponsePackageDto assignRoles(List<Long> roleIds, List<Long> userIds, Boolean isDeleteAssigned) {
        log.debug(">>assignRoles {}, {}, {}", roleIds, userIds, isDeleteAssigned);

        ResponsePackageDto responsePackageDto = new ResponsePackageDto();
        List<UserRole> userRoles = roleService.assignRoles(roleIds, userIds, isDeleteAssigned);
        responsePackageDto.setResult(userRoleMapper.getDtos(userRoles));

        log.debug("<<assignRoles");
        return responsePackageDto;
    }
}
