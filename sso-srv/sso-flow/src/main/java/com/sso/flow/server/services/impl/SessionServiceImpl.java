package com.sso.flow.server.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Joiner;
import com.sso.common.server.dto.ResponsePackageDto;
import com.sso.common.server.config.UserDetails;
import com.sso.common.server.model.entities.Session;
import com.sso.common.server.model.entities.User;
import com.sso.common.server.model.repositories.UserRepository;
import com.sso.common.server.services.impl.GenericServiceImpl;
import com.sso.common.server.utils.DateTimeUtils;
import com.sso.common.server.validators.GenericValidator;
import com.sso.flow.server.model.repositories.SessionRepository;
import com.sso.flow.server.services.SessionService;
import com.sso.flow.server.validators.SessionValidator;
import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.token.KeyBasedPersistenceTokenService;
import org.springframework.security.core.token.Token;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

/**
 * @author Original Author: Alexander Kontarero
 * @version 1/23/2015
 * @see © 2015 SSO - All Rights Reserved
 * See LICENSE file for further details
 */
@Service
@Transactional
public class SessionServiceImpl extends GenericServiceImpl<String, Session>
    implements SessionService {

    private static final String ACCESS_TOKEN_TTL = "accessTokenTTL";
    private static final String REFRESH_TOKEN_TTL = "refreshTokenTTL";

    public static final String TIME_ZONE_NAME = "timeZone";
    public static final String JSON_CONTENT_TYPE = "application/json";

    public static final String REQUEST_HEADER_ACCESS_TOKEN = "Access-Token";
    public static final String REQUEST_HEADER_REFRESH_TOKEN = "Refresh-Token";
    public static final String REQUEST_HEADER_ACCESS_CONTROL_EXPOSE_HEADERS = "Access-Control-Expose-Headers";

    @Autowired
    private KeyBasedPersistenceTokenService tokenService;
    @Autowired
    private CompositeConfiguration compositeConfiguration;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SessionValidator sessionValidator;

    @Override
    public GenericValidator getValidator() {
        log.debug(">>getValidator");
        return sessionValidator;
    }

    @Override
    public CrudRepository<Session, String> getRepository() {
        log.debug(">>getRepository");
        return sessionRepository;
    }

    @Override
    public Session getNewSession(UserDetails userDetails) {
        log.debug(">>getNewSession {}", userDetails);

        Long userId = userDetails.getId();
        String timeZoneName = compositeConfiguration.getString(TIME_ZONE_NAME);
        Long now = Calendar.getInstance(TimeZone.getTimeZone(timeZoneName)).getTimeInMillis();
        checkAllUserSessions(userId, now);

        User user = userRepository.findOne(userId);
        Session session = new Session();
        session.setUser(user);
        session.setAccessTokenTtl(getNewAccessTokenTTL(now));
        session.setRefreshTokenTtl(getNewRefreshTokenTTL(now));

        log.debug("<<getNewSession");
        return session;
    }

    @Override
    public Token getNewToken(String username, String password) {
        log.warn(">>getNewToken {}, {}", username, password);

        String info = Joiner.on(":").join(username, password);
        Token token = tokenService.allocateToken(info);

        log.warn("<<getNewToken");
        return token;
    }

    @Override
    public Token verifyTokenFormat(String token){
        log.warn(">>verifyTokenFormat {}", token);
        return  tokenService.verifyToken(token);
    }

    @Override
    public void deleteByAccessToken(String accessToken) {
        log.debug(">>deleteByAccessToken {}", accessToken);
        sessionRepository.deleteByAccessToken(accessToken);
    }

    @Override
    public Session getSessionByToken(String token, boolean isAccessToken) {
        log.debug(">>getSessionByToken {}, {}", token, isAccessToken);

        Session session;
        if(isAccessToken){
            session = sessionRepository.findByAccessToken(token);
        } else {
            session = sessionRepository.findByRefreshToken(token);
        }

        log.debug("<<getSessionByToken");
        return session;
    }

    @Override
    public boolean isTokenTtlValid(Session session, boolean isAccessToken, Long timeInMillis) {
        log.debug(">>isTokenTtlValid {}, {}, {}", session, isAccessToken, timeInMillis);

        boolean isTokenTtlValid;
        if (isAccessToken) {
            isTokenTtlValid = isAccessTokenTtlValid(session, timeInMillis);
        } else {
            isTokenTtlValid = isRefreshTokenTtlValid(session, timeInMillis);
        }

        log.debug("<<isTokenTtlValid");
        return isTokenTtlValid;
    }

    @Override
    public Timestamp getNewAccessTokenTTL(Long timeInMillis) {
        log.debug(">>getAccessTokenTTL {}", timeInMillis);

        Long result =
            timeInMillis +
                compositeConfiguration.getInt(ACCESS_TOKEN_TTL) * DateTimeUtils.MILLISECONDS_IN_SECOND;

        log.debug("<<getAccessTokenTTL");
        return new Timestamp(result);
    }

    @Override
    public Timestamp getNewRefreshTokenTTL(Long timeInMillis) {
        log.debug(">>getRefreshTokenTTL {}", timeInMillis);

        Long result =
            timeInMillis +
                compositeConfiguration.getInt(REFRESH_TOKEN_TTL) * DateTimeUtils.MILLISECONDS_IN_SECOND;

        log.debug("<<getRefreshTokenTTL");
        return new Timestamp(result);
    }

    @Override
    public void setJsonResponse(HttpServletResponse response, ResponsePackageDto responsePackageDto)
        throws IOException {
        log.debug(">>setJsonResponse {}, {}", response, responsePackageDto);

        response.setContentType(JSON_CONTENT_TYPE);
        // Get the printwriter object from response to write the required json object to the output stream
        PrintWriter out = response.getWriter();
        // Assuming your json object is **jsonObject**, perform the following, it will return your json object
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(responsePackageDto);
        out.print(jsonString);
        out.flush();

        log.debug("<<setJsonResponse");
    }

    @Override
    public Session save(Session session) {
        log.debug(">>save {}", session);

        assertNotNull(session);

        if (StringUtils.isEmpty(session.getId())) {
            session.setId(UUID.randomUUID().toString());
        }

        log.debug("<<save");
        return super.save(session);
    }

    private void checkAllUserSessions(Long userId, Long timeInMillis) {
        log.debug(">>checkAllUserSessions {}, {}", userId, timeInMillis);

        //check and delete all expired sessions for current user
        List<Session> sessions = sessionRepository.findByUserId(userId);
        if (sessions.size() > 0) {
            for (Session session : sessions) {
                if (!isAccessTokenTtlValid(session, timeInMillis) &&
                    !isRefreshTokenTtlValid(session, timeInMillis)) {
                    sessionRepository.delete(session);
                }
            }
        }

        log.debug("<<checkAllUserSessions");
    }

    private boolean isAccessTokenTtlValid(Session session, Long timeInMillis) {
        log.debug(">>isAccessTokenTtlValid {}", session, timeInMillis);
        return session.getAccessTokenTtl().getTime() >= timeInMillis;
    }

    private boolean isRefreshTokenTtlValid(Session session, Long timeInMillis) {
        log.debug(">>isRefreshTokenTtlValid {}", session, timeInMillis);
        return session.getRefreshTokenTtl().getTime() >= timeInMillis;
    }
}
