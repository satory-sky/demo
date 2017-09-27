package com.sso.common.server.model.repositories;

import com.sso.common.server.model.entities.Role;
import com.sso.common.server.model.entities.UserRole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by: Alexander Kontarero
 * Date: 1/02/2016
 * Time: 10:42 AM
 */
@Repository
public interface UserRoleRepository extends CrudRepository<UserRole, Long> {
    @Query(value = "delete from UserRole ur where ur.user.id in :userIds")
    void deleteByUserIds(@Param("userIds") List<Long> userIds);
}
