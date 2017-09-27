package com.sso.common.server.services;

import com.sso.common.server.config.UserDetails;
import com.sso.common.server.model.entities.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Set;

/**
 * @author Original Author: Alexander Kontarero
 * @version 11/12/2014
 * @see © 2014 SSO - All Rights Reserved
 * See LICENSE file for further details
 */
public interface UserService extends GenericService<Long, User> {
    UserDetails getUserDetails();

    Set<String> getAuthorityNames(Collection<GrantedAuthority> grantedAuthorities);
}
