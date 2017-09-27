package com.sso.common.server.dto;

import java.io.Serializable;

/**
 * @author Original Author: Alexander Kontarero
 * @version 11/26/2014
 * @see © 2014 SSO - All Rights Reserved
 * See LICENSE file for further details

 * Abstraction for UI DTOs
 */
public interface GenericEntityDto<ID extends Serializable> extends Serializable {
    void setId(ID id);
}
