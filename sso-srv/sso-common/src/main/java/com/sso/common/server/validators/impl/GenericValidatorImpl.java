package com.sso.common.server.validators.impl;

import com.sso.common.server.validators.GenericValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author Original Author: Alexander Kontarero
 * @version 2/11/2015
 * @see © 2015 SSO - All Rights Reserved
 * See LICENSE file for further details
 */
@Component
public abstract class GenericValidatorImpl<Entity> implements GenericValidator<Entity> {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    public void validateOnSave(Entity entity) {
        log.debug(">>validateOnSave {}", entity);
    }

    public void validateOnUpdate(Entity entity) {
        log.debug(">>validateOnUpdate {}", entity);
    }

    public void validateOnSaveOrUpdate(Entity entity) {
        log.debug(">>validateOnSaveOrUpdate {}", entity);
    }

    public void validateOnPath(Entity entity) {
        log.debug(">>validateOnPath {}", entity);
    }
}
