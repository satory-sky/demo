package au.com.serenity.ms.service.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;

//use Map + overlay securityConfig.properties stuff on top
@PropertySources({
    @PropertySource(value = "classpath:msItemMap.properties"),
    @PropertySource(value = "file:${ms.config.base:.}/securityConfig.properties", ignoreResourceNotFound = true)})
public class MsSupportApplicationBase extends ApplicationBase {
    private static final Logger log = LoggerFactory.getLogger(MsSupportApplicationBase.class);

    @Autowired
    Environment env;

    @Value("${ms.config.base:.}")
    String msConfigBase;
}
