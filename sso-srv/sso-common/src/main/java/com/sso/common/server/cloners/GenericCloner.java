package com.sso.common.server.cloners;

import org.springframework.beans.BeanUtils;

/**
 * @author Original Author: Alexander Kontarero
 * @version 26.02.2015
 * @see © 2015 SSO - All Rights Reserved
 * See LICENSE file for further details
 */
public abstract class GenericCloner<Entity> implements Cloner<Entity> {

    @Override
    public Entity clone(Entity entity, LinksSetter<Entity> linksSetter) {
        Entity clonedEntity = newEntity();
        copyProperties(entity, clonedEntity);
        cleanId(clonedEntity);
        cleanLinkPropertiesOfClonedObject(clonedEntity);
        linksSetter.setLinks(clonedEntity);
        return clonedEntity;
    }

    @Override
    public Entity clone(Entity entity) {
        return clone(entity, new LinksSetter<Entity>() {
            @Override
            public void setLinks(Entity entity) {
            }
        });
    }

    protected abstract Entity newEntity();

    protected void copyProperties(Entity source, Entity clonedObject) {
        BeanUtils.copyProperties(source, clonedObject);
    }

    protected abstract void cleanId(Entity clonedObject);

    protected abstract void cleanLinkPropertiesOfClonedObject(Entity clonedObject);
}
