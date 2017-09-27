package au.com.qantas.ms.service.boot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@PropertySource(value = {"classpath:application.properties"})
@PropertySource(value = {"file:${ms.config.base:.}/classpath:application.properties"}, ignoreResourceNotFound = true)
@Configuration
@EnableAutoConfiguration
public class MsSupportApplicationBase extends SpringBootServletInitializer {
    @Autowired
    Environment env;

    @Value("${ms.config.base:.}")
    String msConfigBase;
}
