package com.sso.common.server.facades.impl;

import com.sso.common.server.dto.GenericEntityDto;
import com.sso.common.server.dto.ResponsePackageDto;
import com.sso.common.server.facades.GenericFacade;
import com.sso.common.server.mappers.GenericMapper;
import com.sso.common.server.services.GenericService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionException;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author Original Author: Alexander Kontarero
 * @version 11/26/2014
 * @see © 2014 SSO - All Rights Reserved
 * See LICENSE file for further details
 */
public abstract class GenericFacadeImpl<EntityDto extends GenericEntityDto, ID extends Serializable, Entity>
        implements GenericFacade<EntityDto, ID> {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private Class<ID> idClass;
    protected static final String REQUEST_HEADER_IDS = "ids";

    @Autowired
    protected ConversionService conversionService;

    public GenericFacadeImpl(Class<ID> idClass) {
        this.idClass = idClass;
    }

    public abstract GenericService<ID, Entity> getService();

    public abstract GenericMapper<EntityDto, Entity> getMapper();

    @Override
    public ResponsePackageDto findById(ID id) {
        log.debug(">>findById {}", id);

        ResponsePackageDto responsePackageDto = new ResponsePackageDto();
        responsePackageDto.setResult(getMapper().getDto(getService().findById(id)));

        log.debug("<<findById");
        return responsePackageDto;
    }

    // Warning! Avoid to override this method.
    // Only "getListByParams" method should be overrided
    @Override
    public final ResponsePackageDto getCustomData(Map<String, String> parameters) {
        log.debug(">>getCustomData {}", parameters);

        ResponsePackageDto responsePackageDto;
        List<ID> ids = getExtractedParameterCollection(parameters, REQUEST_HEADER_IDS, List.class, idClass);
        if (ids != null) {
            responsePackageDto = new ResponsePackageDto();
            responsePackageDto.setResult(getMapper().getDtos(getService().findByIds(ids)));
        } else {
            responsePackageDto = getListByParams(parameters);
            if (responsePackageDto == null) {
                responsePackageDto = new ResponsePackageDto();
                responsePackageDto.setResult(getMapper().getDtos(getService().findAll()));
            }
        }

        log.debug("<<getCustomData");
        return responsePackageDto;
    }

    protected <T> T getExtractedParameter(Map<String, String> parameters, String name, Class<T> primitiveType) {
        log.debug(">>getExtractedParameter {}, {}, {}", parameters, name, primitiveType);

        name = name.toLowerCase();
        if (parameters.containsKey(name)) {
            try {
                String value = parameters.get(name);
                value = URLDecoder.decode(value, "UTF-8");
                return conversionService.convert(value, primitiveType);
            } catch (ConversionException ex) {
                throw new IllegalArgumentException("Incorrect parameter: " + name, ex);
            } catch (UnsupportedEncodingException e) {
                log.error("Exception that should never occur", e);
            }
        }

        log.debug("<<getExtractedParameter");
        return null;
    }

    protected <T, U> T getExtractedParameterCollection(
        Map<String, String> parameters, String name, Class<T> collectionType, Class<U> elementType) {
        log.debug(">>getExtractedParameterCollection {}, {}, {}, {}",
            parameters, name, collectionType, elementType);

        name = name.toLowerCase();
        if (parameters.containsKey(name)) {
            try {
                String value = parameters.get(name);
                value = URLDecoder.decode(value, "UTF-8");
                return (T) conversionService.convert(value,
                        TypeDescriptor.valueOf(String.class),
                        TypeDescriptor.collection(collectionType, TypeDescriptor.valueOf(elementType)));
            } catch (ConversionException ex) {
                throw new IllegalArgumentException("Incorrect parameter: " + name, ex);
            } catch (UnsupportedEncodingException e) {
                log.error("Exception that should never occur", e);
            }
        }

        log.debug("<<getExtractedParameterCollection");
        return null;
    }

    protected ResponsePackageDto getListByParams(Map<String, String> parameters) {
        log.debug(">>getListByParams {}", parameters);
        return null;
    }

    @Override
    public ResponsePackageDto save(EntityDto entityDto) {
        log.debug(">>save {}", entityDto);

        Entity entity = getMapper().getEntity(entityDto);
        ResponsePackageDto responsePackageDto = new ResponsePackageDto();
        responsePackageDto.setResult(getMapper().getDto(getService().save(entity)));

        log.debug("<<save");
        return responsePackageDto;
    }

    @Override
    public ResponsePackageDto save(List<EntityDto> entityDtos) {
        log.debug(">>save {}", entityDtos);

        List<Entity> entities = getMapper().getEntities(entityDtos);
        ResponsePackageDto responsePackageDto = new ResponsePackageDto();
        responsePackageDto.setResult(getMapper().getDtos((Collection<? extends Entity>) getService().save(entities)));

        log.debug("<<save");
        return responsePackageDto;
    }

    @Override
    public ResponsePackageDto patch(EntityDto entityDto) {
        log.debug(">>patch {}", entityDto);

        Entity entity = getMapper().getEntity(entityDto);
        ResponsePackageDto responsePackageDto = new ResponsePackageDto();
        responsePackageDto.setResult(getMapper().getDto(getService().patch(entity)));

        log.debug("<<patch");
        return responsePackageDto;
    }

    @Override
    public ResponsePackageDto delete(ID id) {
        log.debug(">>delete {}", id);

        ResponsePackageDto responsePackageDto = new ResponsePackageDto();
        getService().delete(id);

        log.debug("<<delete");
        return responsePackageDto;
    }

    @Override
    public ResponsePackageDto delete(List<ID> ids) {
        log.debug(">>delete {}", ids);

        ResponsePackageDto responsePackageDto = new ResponsePackageDto();
        getService().delete(ids);

        log.debug("<<delete");
        return responsePackageDto;
    }

    @Override
    public ResponsePackageDto findAll() {
        log.debug(">>findAll");

        ResponsePackageDto responsePackageDto = new ResponsePackageDto();
        responsePackageDto.setResult(getService().findAll());

        log.debug("<<findAll");
        return responsePackageDto;
    }
}
