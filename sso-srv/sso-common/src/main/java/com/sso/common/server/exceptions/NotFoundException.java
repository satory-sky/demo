package com.sso.common.server.exceptions;

/**
 * @author Original Author: Alexander Kontarero
 * @version 29.10.2014
 * @see © 2014 SSO - All Rights Reserved
 * See LICENSE file for further details
 */
public class NotFoundException extends RuntimeException {

    private String resource;

    public NotFoundException(String resource) {
        super();
        this.resource = resource;
    }

    public String getResource() {
        return resource;
    }
}
