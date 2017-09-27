package com.sso.flow.server.impl.repositories;

import com.sso.common.server.model.entities.*;
import com.sso.common.server.model.repositories.*;
import com.sso.flow.server.GenericUnitTest;
import com.sso.flow.server.model.repositories.SessionRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Alex Kontarero on 8/06/2016.
 */
public class SessionRepositoryUnitTest extends GenericUnitTest {
    @Autowired
    SessionRepository sessionRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserRoleRepository userRoleRepository;
    @Autowired
    OrgUnitRepository orgUnitRepository;
    @Autowired
    PositionRepository positionRepository;

    @Test
    public void shouldFindSessionByUserIdSuccessfully() {
        long originalSessionRecordNumber = sessionRepository.count();
        String accessToken = "accessToken";
        String refreshToken = "refreshToken";
        User user = getStoredUser();
        Long userId = user.getId();
        getStoredSession(user, accessToken, refreshToken);

        List<Session> sessions = sessionRepository.findByUserId(userId);

        assertNotNull(sessions);
        assertThat(sessionRepository.count(), is(++originalSessionRecordNumber));
    }

    @Test
    public void shouldFindSessionByUserSuccessfully() {
        long originalSessionRecordNumber = sessionRepository.count();
        String accessToken = "accessToken";
        String refreshToken = "refreshToken";
        User user = getStoredUser();

        getStoredSession(user, accessToken, refreshToken);

        List<Session> sessions = sessionRepository.findByUser(user);

        assertNotNull(sessions);
        assertThat(sessionRepository.count(), is(++originalSessionRecordNumber));
    }

    @Test
    public void shouldDeleteSessionByAccessTokenSuccessfully() {
        long originalSessionRecordNumber = sessionRepository.count();
        String accessToken = "accessToken";
        String refreshToken = "refreshToken";
        User user = getStoredUser();
        Long userId = user.getId();
        getStoredSession(user, accessToken, refreshToken);

        List<Session> sessions = sessionRepository.findByUserId(userId);

        assertNotNull(sessions);
        assertThat(sessionRepository.count(), is(++originalSessionRecordNumber));

        sessionRepository.deleteByAccessToken(accessToken);

        sessions = sessionRepository.findByUserId(userId);
        assertThat(sessionRepository.count(), is(--originalSessionRecordNumber));
    }

    @Test
    public void shouldFindSessionByAccessTokenSuccessfully() {
        long originalSessionRecordNumber = sessionRepository.count();
        String accessToken = "accessToken";
        String refreshToken = "refreshToken";
        User user = getStoredUser();
        getStoredSession(user, accessToken, refreshToken);

        Session session = sessionRepository.findByAccessToken(accessToken);

        assertNotNull(session);
        assertThat(sessionRepository.count(), is(++originalSessionRecordNumber));
    }

    @Test
    public void shouldFindSessionByRefreshTokenSuccessfully() {
        long originalSessionRecordNumber = sessionRepository.count();
        String accessToken = "accessToken";
        String refreshToken = "refreshToken";
        User user = getStoredUser();
        getStoredSession(user, accessToken, refreshToken);

        Session session = sessionRepository.findByRefreshToken(refreshToken);

        assertNotNull(session);
        assertThat(sessionRepository.count(), is(++originalSessionRecordNumber));
    }

    private User getStoredUser() {
        OrgUnit orgUnit = new OrgUnit();
        orgUnit.setName("name");
        orgUnit.setOrgUnitType("type");
        orgUnit.setCode("code");
        orgUnit.setCreated(new Date());
        orgUnitRepository.save(orgUnit);

        Position position = new Position();
        position.setName("captain");
        positionRepository.save(position);

        User user = new User();
        user.setUsername("ginger");
        user.setFirstName("Henry");
        user.setLastName("Morgan");
        user.setEmail("pirate@gmail.com");
        user.setCreated(new Date());
        user.setOrgUnit(orgUnit);
        user.setPosition(position);
        userRepository.save(user);

        Role role = new Role();
        role.setName("power user");
        role.setDescription("description");
        roleRepository.save(role);

        UserRole userRole = new UserRole();
        userRole.setRole(role);
        userRole.setUser(user);
        userRoleRepository.save(userRole);

        return user;
    }

    private Session getStoredSession(User user, String accessToken, String refreshToken) {
        Session session = new Session();
        session.setId("1");
        session.setUser(user);
        session.setAccessToken(accessToken);
        session.setRefreshToken(refreshToken);
        Date now = Calendar.getInstance().getTime();
        session.setAccessTokenTtl(now);
        session.setRefreshTokenTtl(now);
        sessionRepository.save(session);

        return session;
    }
}
