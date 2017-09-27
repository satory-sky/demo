package com.sso.common.server.exceptions;

import com.sso.common.server.enums.ErrorCode;

/**
 * @author Original Author: Alexander Kontarero
 * @version 9/30/2014
 * @see © 2014 SSO - All Rights Reserved
 * See LICENSE file for further details
 */
public class CustomException extends RuntimeException {
    private Object result;
    private ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        super();
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}