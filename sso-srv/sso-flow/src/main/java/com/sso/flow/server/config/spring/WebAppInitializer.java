package com.sso.flow.server.config.spring;

import com.sso.flow.server.config.spring.security.SecurityConfig;
import com.thetransactioncompany.cors.CORSFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.EnumSet;
import java.util.Set;

/**
 * @author Original Author: Alexander Kontarero
 * @version 9/2/2014
 * @see © 2014 SSO - All Rights Reserved
 * See LICENSE file for further details
 */
public class WebAppInitializer implements WebApplicationInitializer {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void onStartup(ServletContext container) {
        // Create the 'root' Spring application context
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(AppConfig.class, SecurityConfig.class, PersistentConfig.class);

        // Manage the lifecycle of the root application context
        container.addListener(new ContextLoaderListener(rootContext));
        container.addListener(new SessionListener());

        // security filter chain - container for security filters (which determine the requests and check session validity)
        setSpringSecurityFilterChain(container);
        // cross-domain javascript support filter
        setCORSFilter(container);
        // encoding filter
        setCharacterEncodingFilter(container);

        //  webflow - create the dispatcher servlet's Spring application context
        AnnotationConfigWebApplicationContext dispatcherServlet = new AnnotationConfigWebApplicationContext();
        dispatcherServlet.register(MvcConfig.class);
        // Register and map the dispatcher servlet
        setDispatcherServlet(container, dispatcherServlet);
    }

    private void setDispatcherServlet(ServletContext container, AnnotationConfigWebApplicationContext dispatcherServlet) {
        ServletRegistration.Dynamic dispatcher =
            container.addServlet("sso-flow", new DispatcherServlet(dispatcherServlet));
        dispatcher.setLoadOnStartup(1);

        Set<String> mappingConflicts = dispatcher.addMapping("/");
        if (!mappingConflicts.isEmpty()) {
            for (String s : mappingConflicts) {
                log.error("Mapping conflict: " + s);
            }
            throw new IllegalStateException(
                "'sso-flow' cannot be mapped to '/' under Tomcat versions <= 7.0.14");
        }
    }

    private void setSpringSecurityFilterChain(ServletContext servletContext) {
        FilterRegistration.Dynamic securityFilterChain = servletContext
            .addFilter("securityFilterChain", new DelegatingFilterProxy("springSecurityFilterChain"));
//        securityFilterChain.addMappingForUrlPatterns(null, true, "/*");
        securityFilterChain.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
    }

    private void setCharacterEncodingFilter(ServletContext context) {
        FilterRegistration characterEncoding = context.addFilter("characterEncodingFilter", CharacterEncodingFilter.class);
        characterEncoding.setInitParameter("encoding", "UTF-8");
        characterEncoding.setInitParameter("forceEncoding", "true");
        characterEncoding.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST, DispatcherType.ASYNC), false, "/*");
    }

    private void setCORSFilter(ServletContext context) {
        FilterRegistration corsFilter = context.addFilter("corsFilter", CORSFilter.class);
        corsFilter.setInitParameter("cors.supportedMethods", "GET, POST, HEAD, PUT, DELETE, OPTIONS, PATCH");
        corsFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST, DispatcherType.ASYNC), false, "/*");
    }

    public class SessionListener implements HttpSessionListener {
        @Override
        public void sessionCreated(HttpSessionEvent event) {
            System.out.println("==== Session is created ====");
            event.getSession().setMaxInactiveInterval(120 * 60);
        }

        @Override
        public void sessionDestroyed(HttpSessionEvent event) {
            System.out.println("==== Session is destroyed ====");
        }
    }
 }