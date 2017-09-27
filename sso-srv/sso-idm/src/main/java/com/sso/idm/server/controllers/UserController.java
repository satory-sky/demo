package com.sso.idm.server.controllers;

import com.sso.common.server.controllers.GenericController;
import com.sso.common.server.dto.ResponsePackageDto;
import com.sso.common.server.dto.idm.UserDto;
import com.sso.common.server.facades.GenericFacade;
import com.sso.idm.server.facades.UserFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Original Author: Alexander Kontarero
 * @version 11/12/2014
 * @see © 2014 SSO - All Rights Reserved
 * See LICENSE file for further details
 */
public class UserController extends GenericController<UserDto, Long> {
    @Autowired
    private UserFacade userFacade;

    @Override
    public GenericFacade<UserDto, Long> getFacade() {
        log.debug(">>getFacade");
        return userFacade;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/details")
    @ResponseBody
    public ResponsePackageDto getUserDetails(HttpServletRequest request, HttpServletResponse response) {
        log.debug(">>getUserDetails {}, {}", request, response);

        ResponsePackageDto responsePackageDto = userFacade.getUserDetails();

        log.debug("<<getUserDetails");
        return responsePackageDto;
    }
}
