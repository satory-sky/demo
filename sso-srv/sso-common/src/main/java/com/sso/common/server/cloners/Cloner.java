package com.sso.common.server.cloners;

/**
 * @author Original Author: Alexander Kontarero
 * @version 26.02.2015
 * @see © 2015 SSO - All Rights Reserved
 * See LICENSE file for further details
 */
public interface Cloner<Entity> {
    Entity clone(Entity entity, LinksSetter<Entity> linksSetter);

    Entity clone(Entity entity);

    interface LinksSetter<Entity> {
        void setLinks(Entity entity);
    }
}
