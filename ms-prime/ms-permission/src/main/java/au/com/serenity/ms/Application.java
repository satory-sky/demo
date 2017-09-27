package au.com.serenity.ms;

import au.com.serenity.ms.service.boot.ApplicationBase;
import au.com.serenity.ms.service.boot.MsSupportApplicationBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class Application extends MsSupportApplicationBase {
    private static final Logger log = LoggerFactory.getLogger(ApplicationBase.class);

    @Autowired
    Environment env;

    public static void main(String[] args) {
        log.info("Starting application... ");
        ApplicationContext context = SpringApplication.run(Application.class, args);
    }
}
