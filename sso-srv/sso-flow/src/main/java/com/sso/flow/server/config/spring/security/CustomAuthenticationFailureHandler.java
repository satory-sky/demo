package com.sso.flow.server.config.spring.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sso.flow.server.services.impl.SessionServiceImpl;
import com.sso.common.server.enums.ErrorCode;
import com.sso.common.server.dto.ResponsePackageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Original Author: Alexander Kontarero
 * @version 10/14/2014
 * @see © 2014 SSO - All Rights Reserved
 * See LICENSE file for further details
 */
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException exception) throws IOException, ServletException {
        log.debug(">>onAuthenticationFailure {}, {}, {}", request, response, exception.getMessage());

        //set our response to OK status
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        response.setContentType(SessionServiceImpl.JSON_CONTENT_TYPE);
        // Get the printwriter object from response to write the required json object to the output stream
        PrintWriter out = response.getWriter();
        // Assuming your json object is **jsonObject**, perform the following, it will return your json object
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(new ResponsePackageDto(ErrorCode.ACCESS_DENIED_ERROR));
        out.print(jsonString);
        out.flush();

        log.debug("<<onAuthenticationFailure");
    }
}
