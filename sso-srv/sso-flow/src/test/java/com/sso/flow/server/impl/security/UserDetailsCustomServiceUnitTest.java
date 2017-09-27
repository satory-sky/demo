package com.sso.flow.server.impl.security;

import com.sso.common.server.config.UserDetails;
import com.sso.common.server.model.entities.*;
import com.sso.common.server.model.repositories.*;
import com.sso.flow.server.GenericUnitTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Date;

import static junit.framework.TestCase.assertNotNull;

/**
 * Created by: Alexander Kontarero
 * Date: 10/03/2016
 * Time: 2:51 PM
 */
public class UserDetailsCustomServiceUnitTest extends GenericUnitTest {
    @Autowired
    UserDetailsService userDetailsService;

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
    public void shouldSuccessfullyLoadUserByUsername(){
        String username = "ginger";
        storeCorrectData(username);

        UserDetails userDetails = (UserDetails) userDetailsService.loadUserByUsername(username);

        assertNotNull(userDetails);
    }

    private void storeCorrectData(String username) {
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
        user.setUsername(username);
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
    }
}
