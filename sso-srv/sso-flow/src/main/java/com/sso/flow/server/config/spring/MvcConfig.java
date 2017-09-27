package com.sso.flow.server.config.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * @author Original Author: Alexander Kontarero
 * @version 9/2/2014
 * @see © 2014 SSO - All Rights Reserved
 * See LICENSE file for further details
 */
@EnableWebMvc
@Configuration
// scan only controllers to avoid duplicate bean creation
@ComponentScan({
    "com.sso.flow.server.controllers",
    "com.sso.common.server.controllers",
    "com.sso.idm.server.controllers"})
public class MvcConfig extends WebMvcConfigurerAdapter {
    private final Logger log = LoggerFactory.getLogger(getClass());

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
        // Spring Security is used instead of security interceptor (another strategy)
        // registry.addInterceptor(new SecurityInterceptor());
//    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("/WEB-INF/resources/");
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    public InternalResourceViewResolver jspViewResolver() {
        InternalResourceViewResolver bean = new InternalResourceViewResolver();
        bean.setPrefix("/WEB-INF/pages/");
        bean.setSuffix(".jsp");
        return bean;
    }
/*
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        final Charset UTF_8 = Charset.forName("UTF-8");
        StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();
        stringConverter.setSupportedMediaTypes(Arrays.asList(
            new MediaType("text", "plain", UTF_8),
            new MediaType("application", "json", UTF_8)));
        converters.add(stringConverter);
        // Add other converters ...
    }
*/
}