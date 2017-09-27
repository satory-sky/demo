package com.sso.idm.server.services.impl;

import com.sso.common.server.model.entities.Role;
import com.sso.common.server.model.entities.User;
import com.sso.common.server.model.entities.UserRole;
import com.sso.common.server.model.repositories.RoleRepository;
import com.sso.common.server.model.repositories.UserRepository;
import com.sso.common.server.model.repositories.UserRoleRepository;
import com.sso.common.server.services.impl.GenericServiceImpl;
import com.sso.common.server.validators.GenericValidator;
import com.sso.idm.server.services.RoleService;
import com.sso.idm.server.validators.RoleValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Original Author: Alexander Kontarero
 * @version 11/11/2014
 * @see © 2014 SSO - All Rights Reserved
 * See LICENSE file for further details
 */
@Service
@Transactional
public class RoleServiceImpl extends GenericServiceImpl<Long, Role> implements RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private RoleValidator roleValidator;

    @Override
    public GenericValidator getValidator() {
        log.debug(">>getValidator");
        return roleValidator;
    }

    @Override
    public CrudRepository<Role, Long> getRepository() {
        log.debug(">>getRepository");
        return roleRepository;
    }

    @Override
    public List<Role> getByUserId(Long id) {
        log.debug(">>getByUserId {}", id);
        return roleRepository.getByUserId(id);
    }

    @Override
    public List<UserRole> assignRoles(List<Long> roleIds, List<Long> userIds, Boolean isDeleteAssigned) {
        log.debug(">>assignRoles {}, {}, {}", roleIds, userIds, isDeleteAssigned);

        if(isDeleteAssigned){
            userRoleRepository.deleteByUserIds(userIds);
        }
        List<UserRole> userRoles = assignRoles(roleIds, userIds);

        log.debug("<<assignRoles");
        return userRoles;
    }

    private List<UserRole> assignRoles(List<Long> roleIds, List<Long> userIds) {
        log.debug(">>assignRoles {}, {}", roleIds, userIds);

        List<UserRole> userRoles = new ArrayList<>();
        for (Long userId : userIds) {
            for (Long roleId : roleIds) {
                User user = userRepository.findOne(userId);
                Role role = roleRepository.findOne(roleId);

                UserRole userRole = new UserRole();
                userRole.setUser(user);
                userRole.setRole(role);
                userRoleRepository.save(userRole);
                userRoles.add(userRole);
            }
        }

        log.debug("<<assignRoles");
        return userRoles;
    }
}
