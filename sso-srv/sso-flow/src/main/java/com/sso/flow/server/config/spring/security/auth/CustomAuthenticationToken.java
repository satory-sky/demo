package com.sso.flow.server.config.spring.security.auth;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * @author Original Author: Alexander Kontarero
 * @version 1/30/2015
 * @see © 2015 SSO - All Rights Reserved
 * See LICENSE file for further details
 */
public class CustomAuthenticationToken extends UsernamePasswordAuthenticationToken {
    public CustomAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }
}
