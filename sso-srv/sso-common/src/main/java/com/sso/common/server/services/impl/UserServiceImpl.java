package com.sso.common.server.services.impl;

import com.sso.common.server.config.UserDetails;
import com.sso.common.server.model.entities.User;
import com.sso.common.server.model.repositories.UserRepository;
import com.sso.common.server.services.UserService;
import com.sso.common.server.validators.GenericValidator;
import com.sso.common.server.validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Original Author: Alexander Kontarero
 * @version 11/12/2014
 * @see © 2014 SSO - All Rights Reserved
 * See LICENSE file for further details
 */
@Service
@Transactional
public class UserServiceImpl extends GenericServiceImpl<Long, User> implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserValidator userValidator;

    @Override
    public GenericValidator getValidator() {
        log.debug(">>getValidator");
        return userValidator;
    }

    @Override
    public CrudRepository<User, Long> getRepository() {
        log.debug(">>getRepository");
        return userRepository;
    }

    @Override
    public UserDetails getUserDetails() {
        log.debug(">>getUserDetails");

        UserDetails userDetails = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            userDetails = (UserDetails) principal;
        }

        log.debug("<<getUserDetails");
        return userDetails;
    }

    @Override
    public Set<String> getAuthorityNames(Collection<GrantedAuthority> grantedAuthorities) {
        log.debug(">>getAuthorityNames");

        Set<String> authorityNames = new HashSet<>();
        for(GrantedAuthority authority : grantedAuthorities) {
            authorityNames.add(authority.toString());
        }

        log.debug("<<getAuthorityNames");
        return authorityNames;
    }
}
