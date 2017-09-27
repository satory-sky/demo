package au.com.serenity.ms.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
//    @Query(value = "select * from role r join user_role u_r on r.id = u_r.role_id join \"user\" u on u_r.user_id = u.id where u.id = :id", nativeQuery = true)
    @Query(value = "select r from Role r inner join r.userRoles ur inner join ur.user u where u.id = :id")
//    @Query(value = "select r from Role r where r.id = :id")
    List<Role> getByUserId(@Param("id") Long id);
}
