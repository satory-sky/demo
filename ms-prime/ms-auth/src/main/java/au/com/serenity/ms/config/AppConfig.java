package au.com.serenity.ms.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {
        // caching classes
        "au.com.serenity.ms.integration.cache",
        // controller
        "au.com.serenity.ms.controller",
        "au.com.serenity.ms.endpoint",
        // service
        "au.com.serenity.ms.service",
        // validator
        "au.com.serenity.ms.validator",
        // mapper
        "au.com.serenity.ms.mapper",
        // config
        "au.com.serenity.ms.config",
        // other configs
        "au.com.serenity.ms.integration.config"})
@EnableSwagger2
public class AppConfig {
    // message resource
    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageBundle = new ReloadableResourceBundleMessageSource();
        messageBundle.setBasenames(new String[]{
            "file:" + System.getProperty("user.home") + "/ms-auth/messages",
            "classpath:messages/messages"});
        messageBundle.setDefaultEncoding("UTF-8");
        // refresh cache once per hour
        messageBundle.setCacheSeconds(60);
        messageBundle.setUseCodeAsDefaultMessage(true);
        return messageBundle;
    }
}
