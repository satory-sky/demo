package au.com.serenity.ms.config;

import au.com.serenity.ms.config.filter.XAuthTokenHeaderFilter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
@EnableAutoConfiguration
public class SecurityConfig {
    @Bean
    public FilterRegistrationBean someFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(xAuthTokenHeaderFilter());
        registration.addUrlPatterns("/v1/security/permissions/*");
        registration.setName("xAuthTokenHeaderFilter");
        registration.setOrder(1);
        return registration;
    }

    @Bean
    public Filter xAuthTokenHeaderFilter() {
        return new XAuthTokenHeaderFilter();
    }
}
