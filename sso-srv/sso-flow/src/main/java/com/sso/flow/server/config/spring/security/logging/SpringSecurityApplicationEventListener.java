package com.sso.flow.server.config.spring.security.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.event.AbstractAuthorizationEvent;
import org.springframework.security.access.event.LoggerListener;
import org.springframework.stereotype.Component;

/**
 * @author Original Author: Alexander Kontarero
 * @version 9/8/2014
 * @see © 2014 SSO - All Rights Reserved
 * See LICENSE file for further details
 */
@Component
class SpringSecurityApplicationEventListener extends LoggerListener {
    private final Logger log = LoggerFactory.getLogger(getClass());

    public void onApplicationEvent(AbstractAuthorizationEvent event) {
        log.debug(" onApplicationEvent " + event);
    }
}