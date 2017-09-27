package com.sso.flow.server.config.spring;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

/**
 * @author Original Author: Alexander Kontarero
 * @version 9/3/2014
 * @see © 2014 SSO - All Rights Reserved
 * See LICENSE file for further details
 */
@Configuration
@ComponentScan({
    "com.sso.flow.server.model.repositories",
    "com.sso.common.server.model.repositories",
    "com.sso.idm.server.model.repositories"})
@EnableJpaRepositories({
    "com.sso.flow.server.model",
    "com.sso.common.server.model",
    "com.sso.idm.server.model"})
//@EntityScan({
//    "com.sso.flow.server.model.entities",
//    "com.sso.common.server.model.entities",
//    "com.sso.idm.server.model.entities"})
@EnableTransactionManagement
public class PersistentConfig {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private static final String DB_DRIVER_CLASS = "db.sso.flow.driver";
    private static final String JDBC_URL_PROPERTY = "db.sso.flow.jdbc.url";
    private static final String SSODB_USER_PROPERTY = "db.sso.flow.user";
    private static final String SSODB_PASSWORD_PROPERTY = "db.sso.flow.password";
    private static final String DB_DIALECT = "db.sso.flow.dialect";
    private static final String DB_PACKAGES_SCAN = "db.sso.flow.packages.scan";

    @Autowired
    private CompositeConfiguration compositeConfiguration;

    // data source
    // Tomcat JDBC connection pool configutation
    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
        DataSource dataSource = new DataSource();
        dataSource.setDriverClassName(compositeConfiguration.getString(DB_DRIVER_CLASS));
        dataSource.setUrl(compositeConfiguration.getString(JDBC_URL_PROPERTY));
        dataSource.setUsername(compositeConfiguration.getString(SSODB_USER_PROPERTY));
        dataSource.setPassword(compositeConfiguration.getString(SSODB_PASSWORD_PROPERTY));
//        dataSource.setTestOnBorrow(true);
//        dataSource.setTestOnReturn(true);
//        dataSource.setTestWhileIdle(true);
//        dataSource.setValidationQuery("select 1");
        dataSource.setRemoveAbandoned(true);
        dataSource.setInitialSize(5);
        dataSource.setMinIdle(10);
        dataSource.setMaxIdle(100);
        dataSource.setMaxActive(200);
        dataSource.setMaxWait(300);
        dataSource.setMaxAge(120);

        return dataSource;
    }

    @Bean(destroyMethod = "destroy")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean =
            new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setDataSource(dataSource());
        localContainerEntityManagerFactoryBean.setPersistenceUnitName("jpaData");
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(hibernateJpaVendorAdapter());
        localContainerEntityManagerFactoryBean.setPackagesToScan(compositeConfiguration.getString(DB_PACKAGES_SCAN));

        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.dialect", compositeConfiguration.getString(DB_DIALECT));
        jpaProperties.put("hibernate.show_sql", "true");
        jpaProperties.put("hibernate.format_sql", "true");
        jpaProperties.put("hibernate.hbm2ddl.auto", "validate");
//        jpaProperties.put("hibernate.connection.isolation", "1");
//        jpaProperties.put("hibernate.jdbc.batch_size", "1000");
//        jpaProperties.put("hibernate.jdbc.fetch_size", "50");
//        jpaProperties.put("hibernate.id.new_generator_mappings", "true");
//        jpaProperties.put("hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.EhCacheRegionFactory");
//        jpaProperties.put("hibernate.cache.use_second_level_cache", "true");
//        jpaProperties.put("hibernate.cache.use_query_cache", "true");
//        jpaProperties.put("hibernate.ejb.event.load", "org.hibernate.event.def.DefaultLoadEventListener");
//        jpaProperties.put("hibernate.current_session_context_class", "thread");
//        jpaProperties.put("hibernate.ejb.naming_strategy", "org.hibernate.cfg.ImprovedNamingStrategy");
//        jpaProperties.put("hibernate.physical_naming_strategy", "fms.util.hibernate.ImprovedNamingStrategy");
//        jpaProperties.put("hibernate.cache.provider_class", "org.hibernate.cache.NoCacheProvider");
//        jpaProperties.put("javax.persistence.validation.mode", "none");
        localContainerEntityManagerFactoryBean.setJpaProperties(jpaProperties);

        return localContainerEntityManagerFactoryBean;
    }

    @Bean
    public HibernateJpaVendorAdapter hibernateJpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

        return jpaTransactionManager;
    }
}
