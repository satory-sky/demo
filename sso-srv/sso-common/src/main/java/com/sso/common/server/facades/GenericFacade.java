package com.sso.common.server.facades;

import com.sso.common.server.dto.GenericEntityDto;
import com.sso.common.server.dto.ResponsePackageDto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author Original Author: Alexander Kontarero
 * @version 11/26/2014
 * @see © 2014 SSO - All Rights Reserved
 * See LICENSE file for further details
 */
public interface GenericFacade<EntityDto extends GenericEntityDto, ID extends Serializable> {
    ResponsePackageDto findById(ID id);

    ResponsePackageDto findAll();

    ResponsePackageDto getCustomData(Map<String, String> headerMap);

    ResponsePackageDto save(EntityDto entityDto);

    ResponsePackageDto save(List<EntityDto> entityDtos);

    ResponsePackageDto patch(EntityDto entityDto);

    ResponsePackageDto delete(ID id);

    ResponsePackageDto delete(List<ID> ids);
}
