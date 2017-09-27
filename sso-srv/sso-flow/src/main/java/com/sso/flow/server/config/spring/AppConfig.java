package com.sso.flow.server.config.spring;

import com.sso.flow.server.config.WebClientDevWrapper;
import com.sso.flow.server.mappers.MapperFacade;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.apache.commons.configuration.AbstractConfiguration;
import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.validation.Validator;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Original Author: Alexander Kontarero
 * @version 9/1/2014
 * @see © 2014 SSO - All Rights Reserved
 * See LICENSE file for further details
 */
@Configuration
@ComponentScan({
    "com.sso.flow.server",
    "com.sso.common.server",
    "com.sso.idm.server"
    })
@EnableScheduling
public class AppConfig {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private static final String HTTP_CLIENT_DEV_MODE = "http.client.dev.mode";
    private static final String UPLOAD_MAX_FILE_SIZE = "upload.max.file.size";
    private final String RESOURCE_FILE_NAME = "sso-flow-dynamic.properties";
    private final int TIMEOUT_IN_MILISECONDS = 40 * 1000;

    // Properties
    @Bean
    PropertiesConfiguration defaultPropertiesConfiguration() {
        PropertiesConfiguration defaultConfig = new PropertiesConfiguration();
        defaultConfig.setDelimiterParsingDisabled(true);
        // for test use:
        // Thread.currentThread().getContextClassLoader().getResource(".").getPath()
        URL url = Thread.currentThread().getContextClassLoader().getResource(RESOURCE_FILE_NAME);
        if (url != null) {
            try {
                defaultConfig.load(url);
            } catch (ConfigurationException e) {
                log.error("problem during loading default resource file: ", e);
            }
        }

        return defaultConfig;
    }

    @Bean
    FileChangedReloadingStrategy reloadingStrategy() {
        return new FileChangedReloadingStrategy();
    }

    @Bean
    PropertiesConfiguration hostPropertiesConfiguration() {
        PropertiesConfiguration hostConfig = new PropertiesConfiguration();
        hostConfig.setDelimiterParsingDisabled(true);
        File file = new File(System.getProperty("user.home") + "/sso/" + RESOURCE_FILE_NAME);
        if (file.isFile() && file.canRead()) {
            try {
                hostConfig.load(file);
            } catch (ConfigurationException e) {
                log.error("problem during loading custom resource file: ", e);
            }
        }
        hostConfig.setReloadingStrategy(reloadingStrategy());

        return hostConfig;
    }
// Another approach for dynamic resources
//    @Bean(name = "messageSource")
//    public ReloadableResourceBundleMessageSource getMessageSource() {
//        ReloadableResourceBundleMessageSource resource = new ReloadableResourceBundleMessageSource();
//        resource.setBasename("classpath:messages");
//        resource.setDefaultEncoding("UTF-8");
//        return resource;
//    }

    @Bean
    CompositeConfiguration compositeConfiguration() {
        Collection<AbstractConfiguration> configurations = new ArrayList<>();
        configurations.add(hostPropertiesConfiguration());
        configurations.add(defaultPropertiesConfiguration());
        CompositeConfiguration configuration = new CompositeConfiguration(configurations);

        return configuration;
    }


    // JMS
    @Bean
    PooledConnectionFactory jmsFactory() {
        PooledConnectionFactory jmsFactory = new PooledConnectionFactory();
        jmsFactory.setConnectionFactory(new ActiveMQConnectionFactory("tcp://localhost:61616"));
        jmsFactory.setIdleTimeout(0);

        return jmsFactory;
    }

    @Bean
    JmsTemplate jmsTemplate() {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(jmsFactory());

        return jmsTemplate;
    }


    // Queue
    @Bean
    ActiveMQQueue fitbitUpdateQueue() {
        return new ActiveMQQueue("UPDATE:FITBIT");
    }

    @Bean
    ActiveMQQueue activityUpdateQueue() {
        return new ActiveMQQueue("UPDATE:ACTIVITY");
    }


    // File multipart resolver
    @Bean
    CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        CompositeConfiguration compositeConfiguration = compositeConfiguration();
        multipartResolver.setMaxUploadSize(
            Long.parseLong(compositeConfiguration.getString(UPLOAD_MAX_FILE_SIZE)));

        return multipartResolver;
    }


    // rest template
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate(httpRequestFactory());
    }

    @Bean
    HttpComponentsClientHttpRequestFactory httpRequestFactory() {
        HttpComponentsClientHttpRequestFactory httpRequestFactory =
                new HttpComponentsClientHttpRequestFactory();
        httpRequestFactory.setReadTimeout(TIMEOUT_IN_MILISECONDS);
        httpRequestFactory.setConnectTimeout(TIMEOUT_IN_MILISECONDS);
        if (Boolean.valueOf(compositeConfiguration().getProperty(HTTP_CLIENT_DEV_MODE).toString())) {
            httpRequestFactory.setHttpClient(
                WebClientDevWrapper.wrapClient(httpRequestFactory.getHttpClient()));
        }
        return httpRequestFactory;
    }

    /*@Bean
    SimpleClientHttpRequestFactory simpleClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory simpleClientHttpRequestFactory =
            new SimpleClientHttpRequestFactory();
        simpleClientHttpRequestFactory.setReadTimeout(TIMEOUT_IN_MILISECONDS);
        simpleClientHttpRequestFactory.setConnectTimeout(TIMEOUT_IN_MILISECONDS);

        return simpleClientHttpRequestFactory;
    }*/

    @Bean
    Validator validator() {
        return new LocalValidatorFactoryBean();
    }

    @Bean
    MapperFacade mapper() {
        return new MapperFacade();
    }

    // handle mapping from String to client's Objects
    @Bean
    ConversionServiceFactoryBean conversionService() {
        return new ConversionServiceFactoryBean();
    }
}
