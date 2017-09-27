package au.com.serenity.ms.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import au.com.serenity.ms.repositories.*;

import java.util.List;

@Repository
public interface PermissionRepository extends CrudRepository<Permission, Long> {
    @Query(value = "select nextval('permission_seq')", nativeQuery = true)
    Long getNextSequenceId();

    @Query(value = "select p.name from Permission p inner join p.rolePermissions rp inner join rp.role r where r.id = :id")
    List<String> getNameByRoleId(@Param("id") Long id);
}
