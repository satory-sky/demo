package com.sso.flow.server.config.spring.security;

import com.sso.flow.server.services.SessionService;
import com.sso.flow.server.services.impl.SessionServiceImpl;
import com.sso.common.server.dto.ResponsePackageDto;
import com.sso.common.server.enums.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Original Author: Alexander Kontarero
 * @version 10/16/2014
 * @see © 2014 SSO - All Rights Reserved
 * See LICENSE file for further details
 */
public class CustomEntryPoint implements AuthenticationEntryPoint {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private SessionService sessionService;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException authException) throws IOException, ServletException {
        log.debug(">>commence {}, {}, {}", request, response, authException.getMessage());

        //set our response to OK status
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        if (response.getContentType() == null || !response.getContentType().contains(
            SessionServiceImpl.JSON_CONTENT_TYPE)) {
            sessionService.setJsonResponse(response, new ResponsePackageDto(ErrorCode.ACCESS_DENIED_ERROR));
        }

        log.debug("<<commence");
    }
}
