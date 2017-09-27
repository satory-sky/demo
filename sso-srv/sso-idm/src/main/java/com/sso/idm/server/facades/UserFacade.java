package com.sso.idm.server.facades;

import com.sso.common.server.dto.ResponsePackageDto;
import com.sso.common.server.dto.idm.UserDto;
import com.sso.common.server.facades.GenericFacade;

/**
 * @author Original Author: Alexander Kontarero
 * @version 11/13/2014
 * @see © 2014 SSO - All Rights Reserved
 * See LICENSE file for further details
 */
public interface UserFacade extends GenericFacade<UserDto, Long> {
    ResponsePackageDto getUserDetails();
}
