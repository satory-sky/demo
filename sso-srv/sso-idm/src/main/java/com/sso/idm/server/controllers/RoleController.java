package com.sso.idm.server.controllers;

import com.sso.idm.server.facades.RoleFacade;
import com.sso.common.server.controllers.GenericController;
import com.sso.common.server.dto.ResponsePackageDto;
import com.sso.common.server.dto.idm.RoleDto;
import com.sso.common.server.facades.GenericFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Original Author: Alexander Kontarero
 * @version 11/11/2014
 * @see © 2014 SSO - All Rights Reserved
 * See LICENSE file for further details
 */
public class RoleController extends GenericController<RoleDto, Long> {
    @Autowired
    private RoleFacade roleFacade;

    @Override
    public GenericFacade<RoleDto, Long> getFacade() {
        log.debug(">>getFacade");
        return roleFacade;
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/user")
    @ResponseBody
    public ResponsePackageDto assignRoles(HttpServletRequest request, HttpServletResponse response,
        @RequestHeader(value = "roleIds", required = true) List<Long> roleIds,
        @RequestHeader(value = "userIds", required = true) List<Long> userIds,
        @RequestHeader(value = "isDeleteAssigned", required = true) Boolean isDeleteAssigned) {
        log.debug(">>assignRoles {}, {}, {}, {}, {}",
            request, response, roleIds, userIds, isDeleteAssigned);

        ResponsePackageDto responsePackageDto = roleFacade.assignRoles(roleIds, userIds, isDeleteAssigned);

        log.debug("<<assignRoles");
        return responsePackageDto;
    }
}
