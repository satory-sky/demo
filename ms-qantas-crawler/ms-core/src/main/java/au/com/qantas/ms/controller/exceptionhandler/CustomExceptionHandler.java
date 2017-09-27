package au.com.qantas.ms.controller.exceptionhandler;

import au.com.qantas.ms.exception.CreateAssertionResponseException;
import au.com.qantas.ms.jsonapi.ErrorObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;
import java.util.Locale;

// ControllerAdvice needs EnableWebMvc annotation (enable WebMvcConfigurationSupport) or
// MVC namespace in config controller scanning
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler({CreateAssertionResponseException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorObject handleCreateAssertionResponseException(WebRequest request, CreateAssertionResponseException e) {
        log.debug(">>handleCreateAssertionResponseException {}, {}", request, e);

        ErrorObject errorObject = new ErrorObject(
            String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), "", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage());

        log.debug("<<handleCreateAssertionResponseException");
        return errorObject;
    }

    @ExceptionHandler({SQLException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorObject handleSQLException(WebRequest request, SQLException e) {
        log.debug(">>handleSQLException {}, {}", request, e);

        ErrorObject errorObject = new ErrorObject(
            String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), "", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage());

        log.debug("<<handleSQLException");
        return errorObject;
    }

    @ExceptionHandler({ClassNotFoundException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorObject handleClassNotFoundException(WebRequest request, ClassNotFoundException e) {
        log.debug(">>handleClassNotFoundException {}, {}", request, e);

        ErrorObject errorObject = new ErrorObject(
            String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), "", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage());

        log.debug("<<handleClassNotFoundException");
        return errorObject;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        log.debug(">>handleMethodArgumentNotValid {}, {}", request, ex);

        ErrorObject result = new ErrorObject();
        Locale currentLocale = LocaleContextHolder.getLocale();
        for (ObjectError objectError : ex.getBindingResult().getAllErrors()) {
            FieldError fieldError = (FieldError) objectError;
            String rejectedValue = fieldError.getRejectedValue() != null ? fieldError.getRejectedValue().toString() : "null";

            result.addError(
                String.valueOf(fieldError.getObjectName() + "." + fieldError.getField()), "",
                rejectedValue, messageSource.getMessage(fieldError, currentLocale));
        }

        log.debug("<<handleMethodArgumentNotValid");
        return super.handleExceptionInternal(ex, result, headers, status, request);
    }
}
