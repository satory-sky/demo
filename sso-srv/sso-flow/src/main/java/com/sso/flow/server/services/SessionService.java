package com.sso.flow.server.services;

import com.sso.common.server.dto.ResponsePackageDto;
import com.sso.common.server.config.UserDetails;
import com.sso.common.server.model.entities.Session;
import com.sso.common.server.services.GenericService;
import org.springframework.security.core.token.Token;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;

/**
 * @author Original Author: Alexander Kontarero
 * @version 1/23/2015
 * @see © 2015 SSO - All Rights Reserved
 * See LICENSE file for further details
 */
public interface SessionService extends GenericService<String, Session> {
    Session getNewSession(UserDetails userDetails);

    Token getNewToken(String username, String password);

    Token verifyTokenFormat(String token);

    public void deleteByAccessToken(String accessToken);

    Session getSessionByToken(String token, boolean isAccessToken);

    boolean isTokenTtlValid(Session session, boolean isAccessToken, Long timeInMillis);

    Timestamp getNewAccessTokenTTL(Long timeInMillis);

    Timestamp getNewRefreshTokenTTL(Long timeInMillis);

    void setJsonResponse(HttpServletResponse response, ResponsePackageDto responsePackageDto) throws IOException;
}
