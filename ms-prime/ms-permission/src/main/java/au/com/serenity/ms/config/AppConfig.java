package au.com.serenity.ms.config;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {
        // caching classes
        "au.com.serenity.ms.integration.cache",
        // controller
        "au.com.serenity.ms.controller",
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
    private static final int TIMEOUT_IN_MILLISECONDS = 2000;
    private static final int MAX_TOTAL_CONNECTIONS = 800;
    private static final int MAX_CONNECTIONS_PER_ROUTE = 700;

    // message resource
    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageBundle = new ReloadableResourceBundleMessageSource();
        messageBundle.setBasenames(new String[]{
            "file:" + System.getProperty("user.home") + "/ms-permission/messages",
            "classpath:messages/messages"});
        messageBundle.setDefaultEncoding("UTF-8");
        // refresh cache once per hour
        messageBundle.setCacheSeconds(60);
        messageBundle.setUseCodeAsDefaultMessage(true);
        return messageBundle;
    }

    // rest template
    @Bean
    RestTemplate restTemplate() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(MAX_TOTAL_CONNECTIONS);
        connectionManager.setDefaultMaxPerRoute(MAX_CONNECTIONS_PER_ROUTE);
        //        connectionManager.setMaxPerRoute(new HttpRoute(new HttpHost("facebook.com")), 20);
        //        connectionManager.setValidateAfterInactivity(4000);
        RequestConfig requestConfig = RequestConfig.custom()
            .setConnectionRequestTimeout(TIMEOUT_IN_MILLISECONDS)
            .setConnectTimeout(TIMEOUT_IN_MILLISECONDS)
            .setSocketTimeout(TIMEOUT_IN_MILLISECONDS)
            .build();
        SocketConfig socketConfig = SocketConfig.custom()
            .setSoKeepAlive(false)
            .setTcpNoDelay(true)
            .build();
        HttpClient httpClient = HttpClientBuilder.create()
            .disableCookieManagement()
            .setConnectionManager(connectionManager)
            .setDefaultRequestConfig(requestConfig)
            .setDefaultSocketConfig(socketConfig)
            .build();
        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpRequestFactory.setHttpClient(httpClient);

        return new RestTemplate(httpRequestFactory);
    }
}
