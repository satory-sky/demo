package com.sso.flow.server.services.impl;

import com.sso.common.server.config.UserDetails;
import com.sso.common.server.model.entities.Role;
import com.sso.common.server.model.entities.User;
import com.sso.common.server.model.repositories.RoleRepository;
import com.sso.common.server.model.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Original Author: Alexander Kontarero
 * @version 9/5/2014
 * @see © 2014 SSO - All Rights Reserved
 * See LICENSE file for further details
 */
@Service
@Transactional(readOnly = true)
public class UserDetailsCustomService implements UserDetailsService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    public UserDetails loadUserByUsername(String username) {
        UserDetails userDetails;
        try {
            log.debug("Looking up in the local database user with name {}", username);
            User user = userRepository.findByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException("Such name have not been registered: " + username);
            } else {
                List<Role> roles = roleRepository.getByUserId(user.getId());
                if (roles.isEmpty()) {
                    throw new UsernameNotFoundException("User " + username + " has no authorities");
                } else {
                    log.debug("Setting user authorities");
                    Set<GrantedAuthority> authorities = getGrantedAuthorities(roles);
                    userDetails = new UserDetails(
                        user.getUsername(),
                        user.getPassword(),
                        user.getEnabled(),
                        user.getAccountNonExpired(),
                        user.getCredentialsNonExpired(),
                        user.getAccountNonLocked(),
                        authorities,
                        user.getId(),
                        user.getFirstName(),
                        user.getMiddleName(),
                        user.getLastName(),
                        user.getEmail(),
                        user.getOrgUnit().getId(),
                        user.getAvatarUrl(),
                        user.getGender(),
                        user.getCreated(),
                        user.getUpdated(),
                        user.getDescription());
                }
            }
        } catch (Exception e) {
            log.error("Unexpected exception while finding user", e);
            throw new InternalAuthenticationServiceException("Unexpected exception while finding user", e);
        }

        return userDetails;
    }

    private Set<GrantedAuthority> getGrantedAuthorities(List<Role> roles) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }
}
