package com.sso.common.server.exceptions;


import com.sso.common.server.dto.ErrorEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Original Author: Alexander Kontarero
 * @version 9/30/2014
 * @see © 2014 SSO - All Rights Reserved
 * See LICENSE file for further details
 */
public class UserValidationException extends RuntimeException {
    private List<ErrorEntry> fields;

    public UserValidationException(List<ErrorEntry> fields) {
        super("user validation error");
        this.fields = fields;
    }
    public UserValidationException(ErrorEntry field) {
        super("user validation error");
        this.fields = new ArrayList<>(1);
        this.fields.add(field);
    }

    public List<ErrorEntry> getFields() {
        return fields;
    }
}