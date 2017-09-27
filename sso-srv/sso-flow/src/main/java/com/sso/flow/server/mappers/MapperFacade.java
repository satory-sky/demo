package com.sso.flow.server.mappers;

import com.sso.common.server.mappers.MapperConfig;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author Original Author: Alexander Kontarero
 * @version 03.12.2014
 * @see © 2014 SSO - All Rights Reserved
 * See LICENSE file for further details
 */
public class MapperFacade extends ConfigurableMapper {

    @Autowired(required = false)
    private List<MapperConfig> mapperConfigs;

    public MapperFacade() {
        super(false);
    }

    @Override
    @PostConstruct
    protected void init() {
        super.init();
    }

    @Override
    protected void configure(MapperFactory factory) {
        factory.registerConcreteType(SortedSet.class, TreeSet.class);
        if (mapperConfigs == null) {
            return;
        }
        for (MapperConfig config : mapperConfigs) {
            config.addToFactory(factory);
        }
    }

    @Override
    public void configureFactoryBuilder(DefaultMapperFactory.Builder builder) {
        super.configureFactoryBuilder(builder);
    }
}
