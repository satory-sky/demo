package com.sso.common.server.controllers;

import com.sso.common.server.dto.GenericEntityDto;
import com.sso.common.server.dto.ResponsePackageDto;
import com.sso.common.server.facades.GenericFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Original Author: Alexander Kontarero
 * @version 11/26/2014
 * @see © 2014 SSO - All Rights Reserved
 * See LICENSE file for further details
 */
public abstract class GenericController<EntityDto extends GenericEntityDto, ID extends Serializable>
    implements GenericControllerInterface<EntityDto, ID> {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private static final String ID_PATH = "/{id}";

    public abstract GenericFacade<EntityDto, ID> getFacade();

    @Override
    @RequestMapping(method = RequestMethod.GET, value = ID_PATH)
    public
    @ResponseBody
    ResponsePackageDto findById(HttpServletRequest request,HttpServletResponse response,
        @PathVariable ID id) {
        log.debug(">>findById {}, {}, {}", request, response, id);

        return getFacade().findById(id);
    }

    @Override
    @RequestMapping(method = RequestMethod.GET)
    public
    @ResponseBody
    ResponsePackageDto getCustomData(HttpServletRequest request, HttpServletResponse response) {
        log.debug(">>getCustomData {}, {}", request, response);

        Map<String, String> headerMap = getParameters(request);
        // get all or filtered/sorted collection
        ResponsePackageDto responsePackageDto = getFacade().getCustomData(headerMap);

        log.debug("<<getCustomData");
        return responsePackageDto;
    }

    protected Map<String, String> getParameters(HttpServletRequest request) {
        // collect url parameters
        // Map<String, String[]> params = request.getParameterMap();
        // collect headers
        Map<String, String> headerMap = new HashMap<>();
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            headerMap.put(key, value);
        }
        return headerMap;
    }

    @Override
    @RequestMapping(method = RequestMethod.POST, value = ID_PATH)
    public
    @ResponseBody
    ResponsePackageDto save(HttpServletRequest request, HttpServletResponse response,
        @Valid @RequestBody EntityDto entityDto) {
        log.debug(">>save {}, {}, {}", request, response, entityDto);

        return getFacade().save(entityDto);
    }

    @Override
    @RequestMapping(method = RequestMethod.POST)
    public
    @ResponseBody
    ResponsePackageDto save(HttpServletRequest request, HttpServletResponse response,
        @Valid @RequestBody List<EntityDto> entityDtos) {
        log.debug(">>save {}, {}, {}", request, response, entityDtos);

        return getFacade().save(entityDtos);
    }

    @Override
    @RequestMapping(method = RequestMethod.PATCH, value = ID_PATH)
    public
    @ResponseBody
    ResponsePackageDto patch(HttpServletRequest request, HttpServletResponse response,
        @PathVariable ID id,
        @Valid @RequestBody EntityDto entityDto) {
        log.debug(">>patch {}, {}, {}, {}", request, response, id, entityDto);

        entityDto.setId(id);
        return getFacade().patch(entityDto);
    }

    @Override
    @RequestMapping(method = RequestMethod.DELETE, value = ID_PATH)
    public
    @ResponseBody
    ResponsePackageDto delete(HttpServletRequest request,HttpServletResponse response,
        @PathVariable ID id) {
        log.debug(">>delete {}, {}, {}", request, response, id);

        return getFacade().delete(id);
    }

    @Override
    @RequestMapping(method = RequestMethod.DELETE)
    public
    @ResponseBody
    ResponsePackageDto delete(HttpServletRequest request,HttpServletResponse response,
        @RequestHeader(value = "ids", required = true) List<ID> ids) {
        log.debug(">>delete {}, {}, {}", request, response, ids);

        return getFacade().delete(ids);
    }
}
