package com.sso.common.server.model.repositories;

import com.sso.common.server.model.entities.OrgUnit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Original Author: Alexander Kontarero
 * @version 9/18/2014
 * @see © 2014 SSO - All Rights Reserved
 * See LICENSE file for further details
 */
@Repository
public interface OrgUnitRepository extends CrudRepository<OrgUnit, Long> {
}
