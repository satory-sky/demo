package com.sso.flow.server.model.repositories;

import com.sso.common.server.model.entities.Session;
import com.sso.common.server.model.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Alex Kontarero on 8/06/2016.
 */
@Repository
public interface SessionRepository extends CrudRepository<Session, String> {
    List<Session> findByUserId(@Param("userId") Long userId);

    List<Session> findByUser(@Param("user") User user);

    void deleteByAccessToken(@Param("accessToken") String accessToken);

    Session findByAccessToken(@Param("accessToken") String accessToken);

    Session findByRefreshToken(@Param("refreshToken") String refreshToken);
}