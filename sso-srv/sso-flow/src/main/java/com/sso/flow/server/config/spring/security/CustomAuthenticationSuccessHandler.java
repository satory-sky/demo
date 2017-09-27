package com.sso.flow.server.config.spring.security;

import com.sso.common.server.dto.ResponsePackageDto;
import com.sso.common.server.config.UserDetails;
import com.sso.common.server.model.entities.Session;
import com.sso.flow.server.services.SessionService;
import com.sso.flow.server.services.impl.SessionServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Original Author: Alexander Kontarero
 * @version 10/14/2014
 * @see © 2014 SSO - All Rights Reserved
 * See LICENSE file for further details
 */
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private String targetUrlParameter;
    @Autowired
    private SessionService sessionService;

    public void setTargetUrlParameter(String targetUrlParameter) {
        this.targetUrlParameter = targetUrlParameter;
    }

    // do some logic here if you want something to be done whenever
    // the user successfully logs in.
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication)
        throws IOException, ServletException {
        log.debug(">>onAuthenticationSuccess {}, {}, {}", request, response, authentication);

        // create session on login
        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();
            String password = userDetails.getPassword();
            Session session = sessionService.getNewSession(userDetails);

            String newAccessToken = sessionService.getNewToken(username, password).getKey();
            String newRefreshToken = sessionService.getNewToken(username, password).getKey();
            session.setAccessToken(newAccessToken);
            session.setRefreshToken(newRefreshToken);
            response.setHeader(SessionServiceImpl.REQUEST_HEADER_ACCESS_CONTROL_EXPOSE_HEADERS,
                SessionServiceImpl.REQUEST_HEADER_ACCESS_TOKEN + "," +
                    SessionServiceImpl.REQUEST_HEADER_REFRESH_TOKEN);
            response.setHeader(SessionServiceImpl.REQUEST_HEADER_ACCESS_TOKEN, newAccessToken);
            response.setHeader(SessionServiceImpl.REQUEST_HEADER_REFRESH_TOKEN, newRefreshToken);

            sessionService.save(session);
            sessionService.setJsonResponse(response, new ResponsePackageDto());
        }
/*
        HttpSession session = request.getSessionByToken();
        session.setAttribute("username", userDetails.getUsername());
        session.setAttribute("authorities", authentication.getAuthorities());

        //set our response to OK status
        response.setStatus(HttpServletResponse.SC_OK);

        //since we have created our custom success handler, its up to us to where
        //we will redirect the user after successfully login
        response.sendRedirect(targetUrlParameter);

        String sessionId = request.getSessionByToken().getId();
        response.setHeader("token", sessionId);
*/
        log.debug("<<onAuthenticationSuccess");
    }
}
