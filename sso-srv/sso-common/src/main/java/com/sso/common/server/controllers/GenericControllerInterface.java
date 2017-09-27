package com.sso.common.server.controllers;

import com.sso.common.server.dto.GenericEntityDto;
import com.sso.common.server.dto.ResponsePackageDto;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

/**
 * @author Original Author: Alexander Kontarero
 * @version 11/26/2014
 * @see © 2014 SSO - All Rights Reserved
 * See LICENSE file for further details
 */
public interface GenericControllerInterface<EntityDto extends GenericEntityDto, ID extends Serializable> {
    ResponsePackageDto findById(HttpServletRequest request, HttpServletResponse response, @PathVariable ID id);

    ResponsePackageDto getCustomData(HttpServletRequest request, HttpServletResponse response);

    ResponsePackageDto save(HttpServletRequest request, HttpServletResponse response, @Valid @RequestBody EntityDto entityDto);

    ResponsePackageDto save(HttpServletRequest request, HttpServletResponse response, @Valid @RequestBody List<EntityDto> entityDtos);

    ResponsePackageDto patch(HttpServletRequest request, HttpServletResponse response, @PathVariable ID id, @Valid @RequestBody EntityDto entityDto);

    ResponsePackageDto delete(HttpServletRequest request, HttpServletResponse response, @PathVariable ID id);

    ResponsePackageDto delete(HttpServletRequest request, HttpServletResponse response, @RequestHeader(value = "ids", required = true) List<ID> ids);
}
