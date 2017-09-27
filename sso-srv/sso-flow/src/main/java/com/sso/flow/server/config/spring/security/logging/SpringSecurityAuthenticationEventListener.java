package com.sso.flow.server.config.spring.security.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * @author Original Author: Alexander Kontarero
 * @version 9/8/2014
 * @see © 2014 SSO - All Rights Reserved
 * See LICENSE file for further details
 */
@Component
public class SpringSecurityAuthenticationEventListener implements ApplicationListener<AbstractAuthenticationEvent> {
    private final Logger log = LoggerFactory.getLogger(getClass());

   @Override
   public void onApplicationEvent(AbstractAuthenticationEvent authenticationEvent) {
      if (authenticationEvent instanceof InteractiveAuthenticationSuccessEvent) {
         // ignores to prevent duplicate logging with AuthenticationSuccessEvent
         return;
      }

      Authentication authentication = authenticationEvent.getAuthentication();
      String auditMessage = "Login attempt with username: " + authentication.getName() + "\t\tSuccess: " +
          authentication.isAuthenticated();
      log.debug(auditMessage);
   }
}