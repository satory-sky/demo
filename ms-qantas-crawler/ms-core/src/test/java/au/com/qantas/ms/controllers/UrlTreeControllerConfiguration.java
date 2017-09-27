package au.com.qantas.ms.controllers;


import au.com.qantas.ms.service.UrlTreeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import static org.mockito.Mockito.mock;

@Profile("test")
@Configuration
public class UrlTreeControllerConfiguration {
    @Bean
    @Primary
    public UrlTreeService urlTreeService1() {
        return mock(UrlTreeService.class);
    }
}