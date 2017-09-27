package com.sso.common.server.services;

import java.io.Serializable;
import java.util.List;

/**
 * @author Original Author: Alexander Kontarero
 * @version 11/26/2014
 * @see © 2014 SSO - All Rights Reserved
 * See LICENSE file for further details
 */
public interface GenericService<ID extends Serializable, Entity> {
    Entity findById(ID id);

    List<Entity> findByIds(List<ID> ids);

    List<Entity> findAll();

    Entity save(Entity entity);

    Iterable<Entity> save(List<Entity> entities);

    Entity patch(Entity entity);

    void delete(ID id);

    void delete(List<ID> ids);
}
