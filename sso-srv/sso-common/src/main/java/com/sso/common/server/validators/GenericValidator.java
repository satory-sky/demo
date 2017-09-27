package com.sso.common.server.validators;

/**
 * @author Original Author: Alexander Kontarero
 * @version 2/11/2015
 * @see © 2015 SSO - All Rights Reserved
 * See LICENSE file for further details
 */
public interface GenericValidator<Entity> {
    void validateOnSave(Entity entity);

    void validateOnUpdate(Entity entity);

    void validateOnSaveOrUpdate(Entity entity);

    void validateOnPath(Entity entity);
}
