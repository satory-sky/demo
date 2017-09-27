package au.com.serenity.ms.service;

import au.com.serenity.ms.dto.PermissionRequestDto;
import au.com.serenity.ms.repositories.PermissionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PermissionService {
    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private PermissionRepository permissionRepository;

    public List<String> getPermissions(PermissionRequestDto permissionRequestDto) {
        log.info(">>getPermissions ()", permissionRequestDto);

        Long id = Long.parseLong(permissionRequestDto.getIdentity());
/*
        Permission permission = permissionRepository.findOne(id);
        List<String> permissions = new ArrayList<>();
        if(permission != null) {
           permissions.add(permission.getName());
        }
*/
        List<String> permissions = permissionRepository.getNameByRoleId(id);

        log.info("<<getPermissions");
        return permissions;
    }
}
