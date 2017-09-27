package com.sso.flow.server.controllers.exceptionhandler;

import com.sso.common.server.dto.ErrorEntry;
import com.sso.common.server.dto.ResponsePackageDto;
import com.sso.common.server.enums.ErrorCode;
import com.sso.common.server.exceptions.CustomException;
import com.sso.common.server.exceptions.MethodNotAllowedException;
import com.sso.common.server.exceptions.NotFoundException;
import com.sso.common.server.exceptions.UserValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Original Author: Alexander Kontarero
 * @version 10/13/2014
 * @see © 2014 SSO - All Rights Reserved
 * See LICENSE file for further details
 */
//ControllerAdvice needs EnableWebMvc annotation (enable WebMvcConfigurationSupport) or 
// MVC namespace in config controller scanning
@ControllerAdvice
public class BaseExceptionHandler extends ResponseEntityExceptionHandler {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @ExceptionHandler({SecurityException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ResponsePackageDto handleSecurityException(Exception e) {
        log.error(e.getMessage(), e);
        return new ResponsePackageDto(ErrorCode.ACCESS_DENIED_ERROR);
    }

    @ExceptionHandler({MethodNotAllowedException.class})
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ResponseBody
    public ResponsePackageDto handleMethodNotAllowed(HttpServletRequest request, Exception e) {
        ResponsePackageDto result = new ResponsePackageDto(ErrorCode.METHOD_NOT_ALLOWED_ERROR);
        result.setMsg(request.getMethod() + ": METHOD_NOT_ALLOWED_ERROR: " + result.getMsg());
        log.error(result.getMsg(), e);
        return result;
    }

    @ExceptionHandler({CustomException.class})
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    @ResponseBody
    public ResponsePackageDto handleCustom(HttpServletRequest request, CustomException e) {
        ResponsePackageDto result = new ResponsePackageDto(e.getErrorCode());
        result.setMsg(request.getMethod() + ": CUSTOM_ERROR: " + result.getMsg());
        log.error(result.getMsg(), e);
        return result;
    }

    @ExceptionHandler({IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponsePackageDto handleRequestException(Exception e) {
        return createValidationErrorResponse(e);
    }

    @ExceptionHandler({NotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponsePackageDto handleMethodNotAllowed(NotFoundException e) {
        ResponsePackageDto result = new ResponsePackageDto(ErrorCode.ENTITY_NOT_FOUND_ERROR);
        result.setMsg(result.getMsg() + " " + e.getResource());
        log.error(result.getMsg(), e);
        return result;
    }

    @ExceptionHandler({UserValidationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponsePackageDto handleUserValidationException(UserValidationException e) {
        ResponsePackageDto result = new ResponsePackageDto(ErrorCode.VALIDATION_ERROR);
        for (ErrorEntry er : e.getFields()) {
            result.addError(er);
        }
        return result;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponsePackageDto handleException(Exception e) {
        return createInternalErrorResponse(e);
    }

    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
        HttpHeaders headers, HttpStatus status,
        WebRequest request) {
        ResponsePackageDto result = new ResponsePackageDto(ErrorCode.VALIDATION_ERROR);
        for (FieldError er : exception.getBindingResult().getFieldErrors()) {
            result.addError(er.getField(), er.getDefaultMessage());
        }
        return new ResponseEntity<Object>(result, headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException e,
        HttpHeaders headers, HttpStatus status,
        WebRequest request) {
        return new ResponseEntity<Object>(createValidationErrorResponse(e), headers, status);
    }

    protected ResponseEntity<Object> handleExceptionInternal(Exception e, Object body,
        HttpHeaders headers, HttpStatus status,
        WebRequest request) {
        return new ResponseEntity<Object>(createInternalErrorResponse(e), headers, status);
    }

    private ResponsePackageDto createInternalErrorResponse(Exception e) {
        ResponsePackageDto result = new ResponsePackageDto(ErrorCode.INTERNAL_ERROR);
        result.setMsg("INTERNAL_ERROR: " + e.getMessage());
        log.error(e.getMessage(), e);
        return result;
    }

    private ResponsePackageDto createValidationErrorResponse(Exception e) {
        ResponsePackageDto result = new ResponsePackageDto(ErrorCode.VALIDATION_ERROR);
        result.setMsg("BAD_REQUEST: " + result.getMsg() + " " + e.getMessage());
        log.error(result.getMsg(), e);
        return result;
    }
}
