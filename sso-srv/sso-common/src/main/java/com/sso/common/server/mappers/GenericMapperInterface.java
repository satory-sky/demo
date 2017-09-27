package com.sso.common.server.mappers;

/**
 * @author Original Author: Alexander Kontarero
 * @version 12/2/2014
 * @see © 2014 SSO - All Rights Reserved
 * See LICENSE file for further details
 */
public interface GenericMapperInterface<EntityDto, Entity> {
    Entity getEntity(EntityDto entityDto);

    EntityDto getDto(Entity entity);
}
