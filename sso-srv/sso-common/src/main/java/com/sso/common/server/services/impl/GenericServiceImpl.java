package com.sso.common.server.services.impl;

import com.sso.common.server.services.GenericService;
import com.sso.common.server.validators.GenericValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.CrudRepository;

import java.io.Serializable;
import java.util.List;

/**
 * @author Original Author: Alexander Kontarero
 * @version 11/26/2014
 * @see © 2014 SSO - All Rights Reserved
 * See LICENSE file for further details
 */
public abstract class GenericServiceImpl<ID extends Serializable, Entity>
    implements GenericService<ID, Entity> {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    public abstract CrudRepository<Entity, ID> getRepository();

    public abstract GenericValidator<Entity> getValidator();

    @Override
    public Entity findById(ID id) {
        log.debug(">>findById {}", id);
        return getRepository().findOne(id);
    }

    @Override
    public List<Entity> findByIds(List<ID> ids) {
        log.debug(">>findByIds {}", ids);
        return (List<Entity>) getRepository().findAll(ids);
    }

    @Override
    public Entity save(Entity entity) {
        log.debug(">>save {}", entity);

        getValidator().validateOnSave(entity);

        log.debug("<<save");
        return getRepository().save(entity);
    }

    @Override
    public Iterable<Entity> save(List<Entity> entities) {
        log.debug(">>save {}", entities);

        for(Entity entity :entities) {
            getValidator().validateOnSave(entity);
        }

        log.debug("<<save");
        return getRepository().save(entities);
    }

    @Override
    public Entity patch(Entity entity) {
        log.debug(">>patch {}", entity);

        getValidator().validateOnPath(entity);

        log.debug("<<patch");
        return getRepository().save(entity);
    }

    @Override
    public List<Entity> findAll() {
        log.debug(">>findAll");
        return (List<Entity>) getRepository().findAll();
    }

    @Override
    public void delete(ID id) {
        log.debug(">>delete {}", id);
        getRepository().delete(id);
    }

    @Override
    public void delete(List<ID> ids) {
        log.debug(">>delete {}", ids);
        Iterable<Entity> entities = getRepository().findAll(ids);
        getRepository().delete(entities);
    }
}