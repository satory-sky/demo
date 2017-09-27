package com.sso.common.server.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author Original Author: Alexander Kontarero
 * @version 9/1/2014
 * @see © 2014 SSO - All Rights Reserved
 * See LICENSE file for further details
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ErrorEntry {
    private final String object;
    private final String objectId;
    private final String field;
    private final String fieldValue;
    private final String msgCode;

    public ErrorEntry(String field, String msgCode) {
        this(field, null, msgCode);
    }

    public ErrorEntry(String object, String objectId, String field, String fieldValue, String msgCode) {
        this.object = object;
        this.objectId = objectId;
        this.field = field;
        this.fieldValue = fieldValue;
        this.msgCode = msgCode;
    }

    public ErrorEntry(String field, String fieldValue, String msgCode) {
        this(null, null, field, fieldValue, msgCode);
    }

    public String getObject() {
        return object;
    }

    public String getObjectId() {
        return objectId;
    }

    public String getField() {
        return field;
    }

    public String getMsgCode() {
        return msgCode;
    }

    public String getFieldValue() {
        return fieldValue;
    }
}
