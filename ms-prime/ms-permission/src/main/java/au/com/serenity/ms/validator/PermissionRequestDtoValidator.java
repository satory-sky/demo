package au.com.serenity.ms.validator;

import au.com.serenity.ms.dto.PermissionRequestDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PermissionRequestDtoValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return PermissionRequestDto.class.isAssignableFrom(clazz)|| byte[].class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PermissionRequestDto permissionRequestDto = (PermissionRequestDto) target;

        // business logic validation
        String clientApplication = permissionRequestDto.getClientApplication();
        if (!"123".equals(clientApplication)) {
            errors.rejectValue("clientApplication", "NotValid.permissionRequestDto.clientApplication", "field is not valid");
        }
    }
}