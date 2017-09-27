package com.sso.flow.server.config.spring.security;

import com.sso.flow.server.services.SessionService;
import com.sso.flow.server.services.impl.SessionServiceImpl;
import com.sso.common.server.dto.ResponsePackageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Original Author: Alexander Kontarero
 * @version 12/9/2014
 * @see © 2014 SSO - All Rights Reserved
 * See LICENSE file for further details
 */
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private SessionService sessionService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException, ServletException {
        log.debug(">>onLogoutSuccess {}, {}, {}", request, response, authentication);

        // clear session on logout
        if (authentication != null && authentication.isAuthenticated()) {
            String accessToken = request.getHeader(SessionServiceImpl.REQUEST_HEADER_ACCESS_TOKEN);
            if(accessToken != null) {
                sessionService.deleteByAccessToken(accessToken);
            }
            sessionService.setJsonResponse(response, new ResponsePackageDto());
        }

        log.debug("<<onLogoutSuccess");
    }
}
