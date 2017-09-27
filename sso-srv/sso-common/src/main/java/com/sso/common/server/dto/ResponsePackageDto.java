package com.sso.common.server.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sso.common.server.enums.ErrorCode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Original Author: Alexander Kontarero
 * @version 9/1/2014
 * @see © 2014 SSO - All Rights Reserved
 * See LICENSE file for further details
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ResponsePackageDto implements Serializable {

    @JsonIgnore
    private ErrorCode errorCode;

    private Integer code;
    private String msg;
    private List<ErrorEntry> error;
    private Object result;

    public ResponsePackageDto() {
        this(ErrorCode.SUCCESS);
    }

    public ResponsePackageDto(int code, String error) {
        this.code = code;
        this.msg = error;
    }

    public ResponsePackageDto(ErrorCode code) {
        this.errorCode = code;
        this.code = code.getCode();
        this.msg = code.getDescription();
    }

    public static ResponsePackageDto fromErrors(ErrorCode code, List<ErrorEntry> errors) {
        if (errors != null && !errors.isEmpty()) {
            ResponsePackageDto responsePackageDto = new ResponsePackageDto(code);
            responsePackageDto.addErrors(errors);
            return responsePackageDto;
        } else {
            return new ResponsePackageDto(ErrorCode.SUCCESS);
        }
    }

    public Integer getCode() {
        return code;
    }

    public ResponsePackageDto setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public ResponsePackageDto setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Object getResult() {
        return result;
    }

    public ResponsePackageDto setResult(Object result) {
        this.result = result;
        return this;
    }

    public ResponsePackageDto addError(String key, String value) {
        createErrosIfNull();
        error.add(new ErrorEntry(key, value));
        return this;
    }

    public ResponsePackageDto addError(ErrorEntry entry) {
        createErrosIfNull();
        error.add(entry);
        return this;
    }

    public ResponsePackageDto addErrors(List<ErrorEntry> errors) {
        createErrosIfNull();
        error.addAll(errors);
        return this;
    }

    private void createErrosIfNull() {
        if (error == null) {
            error = new ArrayList<>();
        }
    }

    @JsonIgnore
    public boolean success(){
        return ErrorCode.SUCCESS.equals(errorCode);
    }

    public List<ErrorEntry> getError() {
        return error;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.code = errorCode.getCode();
        this.msg = errorCode.getDescription();
    }
}
