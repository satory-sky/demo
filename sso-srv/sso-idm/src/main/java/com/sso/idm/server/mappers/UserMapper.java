package com.sso.idm.server.mappers;

import com.sso.common.server.dto.idm.UserDto;
import com.sso.common.server.mappers.GenericMapper;
import com.sso.common.server.model.entities.User;
import org.springframework.stereotype.Component;

/**
 * @author Original Author: Alexander Kontarero
 * @version 1/27/2015
 * @see © 2015 SSO - All Rights Reserved
 * See LICENSE file for further details
 */
@Component
public class UserMapper extends GenericMapper<UserDto, User> {
    public UserMapper() {
        super(UserDto.class, User.class);
    }
}