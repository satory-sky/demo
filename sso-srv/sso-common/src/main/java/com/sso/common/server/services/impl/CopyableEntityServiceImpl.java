package com.sso.common.server.services.impl;

import com.sso.common.server.cloners.Cloner;
import com.sso.common.server.services.CopyableEntityService;

import java.io.Serializable;

/**
 * @author Original Author: Alexander Kontarero
 * @version 27.02.2015
 * @see © 2015 SSO - All Rights Reserved
 * See LICENSE file for further details
 */
public abstract class CopyableEntityServiceImpl<ID extends Serializable, Entity> extends GenericServiceImpl<ID, Entity>
        implements CopyableEntityService<ID, Entity> {

    public abstract Cloner<Entity> getCloner();

    @Override
    public Entity copy(Entity source, Cloner.LinksSetter<Entity> linksSetter) {
        log.debug(">>copy {}", source);
        Cloner<Entity> cloner = getCloner();
        Entity destination;
        if (linksSetter == null) {
            destination = cloner.clone(source);
        } else {
            destination = cloner.clone(source, linksSetter);
        }
        destination = save(destination);
        log.debug("<<copy");
        return destination;
    }

    @Override
    public final Entity copy(Entity source) {
        log.debug(">>copy {}", source);
        Entity destination = copy(source, null);
        log.debug("<<copy");
        return destination;
    }
}
