package com.sso.common.server.mappers;


import ma.glasnost.orika.MapperFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Original Author: Alexander Kontarero
 * @version 12/2/2014
 * @see © 2014 SSO - All Rights Reserved
 * See LICENSE file for further details
 */
public abstract class GenericMapper<EntityDto, Entity> implements GenericMapperInterface<EntityDto, Entity> {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final Class<EntityDto> dtoType;
    private final Class<Entity> entityType;
    @Autowired
    private MapperFacade mapperFacade;

    public GenericMapper(Class<EntityDto> dtoType, Class<Entity> entityType) {
        this.dtoType = dtoType;
        this.entityType = entityType;
    }

    public Entity getEntity(EntityDto entityDto) {
        log.debug(">>getEntity {} ", entityDto);
        return mapperFacade.map(entityDto, entityType);
    }

    public EntityDto getDto(Entity entity) {
        log.debug(">>getDto {} ", entity);
        return mapperFacade.map(entity, dtoType);
    }

    public final List<Entity> getEntities(Collection<? extends EntityDto> dtos) {
        log.debug(">>getEntities {}", dtos);
        List<Entity> resultList = new ArrayList<>(dtos.size());
        for (EntityDto dto : dtos) {
            resultList.add(getEntity(dto));
        }
        return resultList;
    }

    public final List<EntityDto> getDtos(Collection<? extends Entity> entities) {
        log.debug(">>getDtos {}", entities);
        List<EntityDto> resultList = new ArrayList<>(entities.size());
        for (Entity entity : entities) {
            resultList.add(getDto(entity));
        }
        return resultList;
    }

    protected MapperFacade getMapperFacade() {
        return this.mapperFacade;
    }
}
