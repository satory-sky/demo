package au.com.serenity.ms.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrgUnitRepository extends CrudRepository<OrgUnit, Long> {
}
