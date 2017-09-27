package au.com.qantas.ms;

import au.com.qantas.ms.service.boot.MsSupportApplicationBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class Application extends MsSupportApplicationBase {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    @Autowired
    Environment env;

    public static void main(String[] args) {
        log.info("Starting application... ");
        SpringApplication.run(Application.class, args);
    }
}
