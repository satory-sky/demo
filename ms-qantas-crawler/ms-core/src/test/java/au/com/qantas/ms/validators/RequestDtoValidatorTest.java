package au.com.qantas.ms.validators;

import au.com.qantas.ms.dto.RequestDto;
import au.com.qantas.ms.validator.RequestDtoValidator;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;

import static org.junit.Assert.assertTrue;

public class RequestDtoValidatorTest {
    private RequestDtoValidator requestDtoValidator;

    @Test
    public void ShouldSuccessfullyPassValidationForValidData() throws Exception {
        requestDtoValidator = new RequestDtoValidator();
        final String url = "https://trello.com/";
        RequestDto requestDto = new RequestDto();
        requestDto.setBaseUrl(url);
        requestDto.setSearchDepth(2);
        Errors errors = new BindException(requestDto, "requestDto");
        requestDtoValidator.validate(requestDto, errors);

        assertTrue(CollectionUtils.isEmpty(errors.getAllErrors()));
    }
}
