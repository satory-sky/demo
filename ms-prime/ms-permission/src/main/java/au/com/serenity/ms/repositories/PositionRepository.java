package au.com.serenity.ms.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends CrudRepository<Position, Long> {
    @Query(value = "select nextval('position_seq')", nativeQuery = true)
    Long getNextSequenceId();
}
