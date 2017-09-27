package au.com.qantas.ms.validator;

import au.com.qantas.ms.dto.RequestDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class RequestDtoValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return RequestDto.class.isAssignableFrom(clazz)|| byte[].class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RequestDto requestDto = (RequestDto) target;

        // business logic validation
        if (requestDto.getSearchDepth() < 1 || requestDto.getSearchDepth() > 10) {
            errors.rejectValue("searchDepth", "NotValid.requestDto.searchDepth", "field is not valid");
        }
    }
}