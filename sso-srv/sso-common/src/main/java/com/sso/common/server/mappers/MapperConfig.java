package com.sso.common.server.mappers;

import ma.glasnost.orika.MapperFactory;

/**
 * @author Original Author: Alexander Kontarero
 * @version 03.12.2014
 * @see © 2014 SSO - All Rights Reserved
 * See LICENSE file for further details
 */
public interface MapperConfig {
    void addToFactory(MapperFactory factory);
}
