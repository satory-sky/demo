package com.sso.flow.server.config.spring.security.auth;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.sso.common.server.dto.ResponsePackageDto;
import com.sso.common.server.enums.ErrorCode;
import com.sso.common.server.model.entities.Session;
import com.sso.flow.server.services.SessionService;
import com.sso.flow.server.services.impl.SessionServiceImpl;
import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.token.Token;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.MessageFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * @author Original Author: Alexander Kontarero
 * @version 1/28/2015
 * @see © 2015 SSO - All Rights Reserved
 * See LICENSE file for further details
 */
public class TokenSecurityContextRepository implements SecurityContextRepository {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private AuthenticationManager authenticationManager;
    @Autowired
    private SessionService sessionService;
    @Autowired
    CompositeConfiguration compositeConfiguration;

    public TokenSecurityContextRepository(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    /**
     * Gets the security context for the current request (if available) and returns it.
     * <p/>
     * If the session is null, the context object is null or the context object stored in the session
     * is not an instance of {@code SecurityContext}, a new context object will be generated and
     * returned.
     */
    public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {
        log.warn(">>loadContext {}", requestResponseHolder);

        HttpServletRequest request = requestResponseHolder.getRequest();
        HttpServletResponse response = requestResponseHolder.getResponse();

        String timeZoneName = compositeConfiguration.getString(SessionServiceImpl.TIME_ZONE_NAME);
        Long now = Calendar.getInstance(TimeZone.getTimeZone(timeZoneName)).getTimeInMillis();
        // use access token
        String accessToken = request.getHeader(SessionServiceImpl.REQUEST_HEADER_ACCESS_TOKEN);
        SecurityContext context = resolveContextFromRequest(request, response, accessToken, true, now);

        if (context == null) {
            context = getNewContext();
        }

        log.warn("<<loadContext");
        return context;
    }

    public void saveContext(SecurityContext context, HttpServletRequest request,
        HttpServletResponse response) {
    }

    public boolean containsContext(HttpServletRequest request) {
        // should return 'false' for context recreation each time
        return false;
        //return resolveContextFromRequest(request) != null;
    }

    /**
     * By default, calls {@link SecurityContextHolder#createEmptyContext()} to obtain a new context (there should be
     * no context present in the holder when this method is called). Using this approach the context creation
     * strategy is decided by the {@link org.springframework.security.core.context.SecurityContextHolderStrategy} in use. The default implementations
     * will return a new <tt>SecurityContextImpl</tt>.
     *
     * @return a new SecurityContext instance. Never null.
     */
    private SecurityContext getNewContext() {
        log.warn(">>getNewContext");
        return SecurityContextHolder.createEmptyContext();
    }

    private SecurityContext resolveContextFromRequest(HttpServletRequest request,
        HttpServletResponse response, String token, boolean isAccessToken, Long timeInMillis) {
        log.warn(">>resolveContextFromRequest {}, {}, {}, {}",
            request, response, token, isAccessToken, timeInMillis);

        // token validation
        SecurityContext context = null;
        try {
            String tokenName = isAccessToken ?
                ("'" + SessionServiceImpl.REQUEST_HEADER_ACCESS_TOKEN + "'") :
                ("'" + SessionServiceImpl.REQUEST_HEADER_REFRESH_TOKEN + "'");
            if (!StringUtils.isEmpty(token)) {
                // token format validation
                Token verifiedToken = sessionService.verifyTokenFormat(token);
                if (verifiedToken == null) {
                    log.warn(tokenName + " has wrong format");
                } else {
                    // check if token exists
                    Session session = sessionService.getSessionByToken(token, isAccessToken);
                    if (session == null) {
                        log.warn(tokenName + " is wrong");
                    } else {
                        // token TTL validation
                        boolean isTokenTtlValid = sessionService.isTokenTtlValid(
                            session, isAccessToken, timeInMillis);
                        if (isTokenTtlValid) {
                            context = getContextForToken(
                                verifiedToken, session, response, isAccessToken, timeInMillis);
                        } else {
                            log.warn(tokenName + " was expired");

                            ResponsePackageDto responsePackageDto =
                                new ResponsePackageDto(ErrorCode.ACCESS_DENIED_ERROR);
                            responsePackageDto.setResult(tokenName + " was expired");
                            sessionService.setJsonResponse(response, responsePackageDto);

                            if (isAccessToken) {
                                // try to use next token
                                token = request.getHeader(SessionServiceImpl.REQUEST_HEADER_REFRESH_TOKEN);
                                context = resolveContextFromRequest(
                                    request, response, token, false, timeInMillis);
                            }
                        }
                    }
                }
            } else {
                log.warn(tokenName + " does not exist");
            }
        } catch (Exception e) {
            log.warn(MessageFormat.format("Error | {0}", "'Security Context' was not created"), e);
        }

        log.warn("<<resolveContextFromRequest");
        return context;
    }

    private SecurityContext getContextForToken(Token token, Session session, HttpServletResponse response,
        boolean isAccessToken, Long timeInMillis) {
        log.warn(">>getContextForToken {}, {}, {}", session, response, isAccessToken, timeInMillis);

        SecurityContext context = null;
        // username and password could be obtained from DB 'User' table via 'session.getUserId()'
        // however it may be receive from token (in the current implementation) for speed up
        Iterable<String> values = Splitter.on(":").split(token.getExtendedInformation());
        String username = Iterables.get(values, 0);
        String password = Iterables.get(values, 1);

        Authentication auth = authenticationManager.authenticate(
            new CustomAuthenticationToken(username, password));
        context = getNewContext();
        context.setAuthentication(auth);

        // update session on authorized access
        session.setAccessTokenTtl(sessionService.getNewAccessTokenTTL(timeInMillis));
        session.setRefreshTokenTtl(sessionService.getNewRefreshTokenTTL(timeInMillis));

        if (!isAccessToken) {
            String newAccessToken = sessionService.getNewToken(username, password).getKey();
            String newRefreshToken = sessionService.getNewToken(username, password).getKey();
            session.setAccessToken(newAccessToken);
            session.setRefreshToken(newRefreshToken);
            response.setHeader(SessionServiceImpl.REQUEST_HEADER_ACCESS_CONTROL_EXPOSE_HEADERS,
                SessionServiceImpl.REQUEST_HEADER_ACCESS_TOKEN + "," +
                    SessionServiceImpl.REQUEST_HEADER_REFRESH_TOKEN);
            response.setHeader(SessionServiceImpl.REQUEST_HEADER_ACCESS_TOKEN, newAccessToken);
            response.setHeader(SessionServiceImpl.REQUEST_HEADER_REFRESH_TOKEN, newRefreshToken);
        }
        sessionService.save(session);

        log.warn("<<getContextForToken");
        return context;
    }
}
