package com.sso.common.server.model.repositories;

import com.sso.common.server.model.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by: Alexander Kontarero
 * Date: 1/02/2016
 * Time: 10:42 AM
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
