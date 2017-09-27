package com.sso.idm.server.facades.impl;

import com.sso.common.server.dto.ResponsePackageDto;
import com.sso.common.server.dto.idm.UserDto;
import com.sso.common.server.config.UserDetails;
import com.sso.common.server.facades.impl.GenericFacadeImpl;
import com.sso.common.server.mappers.GenericMapper;
import com.sso.common.server.model.entities.User;
import com.sso.common.server.services.GenericService;
import com.sso.common.server.services.UserService;
import com.sso.idm.server.mappers.UserMapper;
import com.sso.idm.server.facades.UserFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Original Author: Alexander Kontarero
 * @version 11/13/2014
 * @see © 2014 SSO - All Rights Reserved
 * See LICENSE file for further details
 */
@Service
public class UserFacadeImpl extends GenericFacadeImpl<UserDto, Long, User> implements UserFacade {
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;

    public UserFacadeImpl() {
        super(Long.class);
    }

    @Override
    public GenericService<Long, User> getService() {
        log.debug(">>getService");
        return userService;
    }

    @Override
    public GenericMapper<UserDto, User> getMapper() {
        log.debug(">>getMapper");
        return userMapper;
    }

    @Override
    public ResponsePackageDto getUserDetails() {
        log.debug(">>getUserDetails");

        ResponsePackageDto responsePackageDto = new ResponsePackageDto();
        UserDetails userDetails = userService.getUserDetails();
        responsePackageDto.setResult(userDetails);

        log.debug("<<getUserDetails");
        return responsePackageDto;
    }
}
