package com.sso.idm.server.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

/**
 * @author Original Author: Alexander Kontarero
 * @version 9/1/2014
 * @see © 2014 SSO - All Rights Reserved
 * See LICENSE file for further details
 */
public enum ErrorType {

    SYSTEM_ERROR(0),
    DEGIRO_SERVICE_TEMPORARILY_UNAVAILABLE(1),
    UNKNOWN_ERROR(2);

    private final int value;

    ErrorType(int i) {
        value = i;
    }

    @JsonValue
    public final int getNumber() {
        return value;
    }

    public static ErrorType valueOf(int value) {
        switch (value) {
            case 0:
                return SYSTEM_ERROR;
            case 1:
                return DEGIRO_SERVICE_TEMPORARILY_UNAVAILABLE;
            case 2:
                return UNKNOWN_ERROR;
            default:
                return null;
        }
    }

    public static class ErrorTypeEnumDeserializer extends JsonDeserializer<ErrorType> {
        @Override
        public ErrorType deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException,
            JsonProcessingException {
            return ErrorType.valueOf(jp.getValueAsInt());
        }
    }


}
