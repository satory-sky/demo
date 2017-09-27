package com.sso.common.server.model.repositories;

import com.sso.common.server.model.entities.Position;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by: Alexander Kontarero
 * Date: 1/02/2016
 * Time: 10:42 AM
 */
@Repository
public interface PositionRepository extends CrudRepository<Position, Long> {
    @Query(value = "select nextval('position_seq')", nativeQuery = true)
    public Long getNextSequenceId();
}
