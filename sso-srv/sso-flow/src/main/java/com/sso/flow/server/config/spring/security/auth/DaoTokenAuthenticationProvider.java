package com.sso.flow.server.config.spring.security.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Original Author: Alexander Kontarero
 * @version 1/30/2015
 * @see © 2015 SSO - All Rights Reserved
 * See LICENSE file for further details
 */
public class DaoTokenAuthenticationProvider extends DaoAuthenticationProvider {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails,
        UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        Object salt = null;

        if (this.getSaltSource() != null) {
            salt = this.getSaltSource().getSalt(userDetails);
        }

        if (authentication.getCredentials() == null) {
            log.debug("Authentication failed: no credentials provided");

            throw new BadCredentialsException(messages.getMessage(
                "AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"), userDetails);
        }

        String presentedPassword = authentication.getCredentials().toString();

        if (authentication instanceof CustomAuthenticationToken) {
            log.debug("Provider is working with token authorization");
        } else if (!getPasswordEncoder()
            .isPasswordValid(userDetails.getPassword(), presentedPassword, salt)) {
            log.debug("Authentication failed: password does not match stored value");

            throw new BadCredentialsException(messages.getMessage(
                "AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"), userDetails);
        }
    }
}
