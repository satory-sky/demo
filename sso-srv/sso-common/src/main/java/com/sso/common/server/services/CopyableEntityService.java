package com.sso.common.server.services;

import com.sso.common.server.cloners.Cloner;

import java.io.Serializable;

/**
 * @author Original Author: Alexander Kontarero
 * @version 27.02.2015
 * @see © 2015 SSO - All Rights Reserved
 * See LICENSE file for further details
 */
public interface CopyableEntityService<ID extends Serializable, Entity> extends GenericService<ID, Entity> {
    Entity copy(Entity source, Cloner.LinksSetter<Entity> linksSetter);

    Entity copy(Entity source);
}
