package au.com.serenity.ms.exception;

import javax.validation.ValidationException;

public class CreateAssertionResponseException extends ValidationException {
    public CreateAssertionResponseException(String message) {
        super(message);
    }
}
