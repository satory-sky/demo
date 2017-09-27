package au.com.serenity.ms.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends CrudRepository<UserRole, Long> {
    @Query(value = "delete from UserRole ur where ur.user.id in :userIds")
    void deleteByUserIds(@Param("userIds") List<Long> userIds);
}
