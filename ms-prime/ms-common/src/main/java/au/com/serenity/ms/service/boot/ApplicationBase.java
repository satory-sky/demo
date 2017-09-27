package au.com.serenity.ms.service.boot;

import au.com.serenity.ms.service.filter.CorrelatedThreadName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import javax.servlet.Filter;

@PropertySources({
    @PropertySource(value = "classpath:application.properties"),
    @PropertySource(value = "file:${ms.config.base:.}/classpath:application.properties", ignoreResourceNotFound = true)})
@Configuration
@EnableAutoConfiguration
public abstract class ApplicationBase extends SpringBootServletInitializer {

    private static final Logger log = LoggerFactory.getLogger(ApplicationBase.class);

    @Autowired
    Environment env;

    private static String LICENSE = "Serenity license 0.x";
    private static String LICENSE_URL = "https://api.serenity.com.au/master/LICENSE";

    protected String title;
    protected String groupName;
    protected String description;
    protected String tosURL;
    protected Contact contactInfo;

    // returns app paths
    // protected abstract Predicate<String> getAppPaths();
    // protected abstract List<SecurityScheme> securitySchemes();
    // protected abstract List<SecurityContext> securityContexts();

    public Docket apiDetails() {
        return new Docket(DocumentationType.SWAGGER_2).groupName(getGroupName()).apiInfo(apiInfo()).select()

            .apis(RequestHandlerSelectors.any())

            .paths(PathSelectors.any())

            .build().genericModelSubstitutes(ResponseEntity.class);
        /*
		 * .paths(getAppPaths()) .build()
		 * .genericModelSubstitutes(ResponseEntity.class);
		 * .securitySchemes(securitySchemes())
		 * .securityContexts(securityContexts());
		 */
    }

    protected ApiInfo apiInfo() {
        return new ApiInfoBuilder().title(getTitle()).description(getDescription()).termsOfServiceUrl(getTosURL())
            .contact(getContactInfo()).license(LICENSE).licenseUrl(LICENSE_URL).version("2.0").build();

    }

    /**
     * CLUDGE to work around JSonSubType anno ruining swagger gen via maven
     * plugin + springfox missing items (OOPS)
     *
     * @return
     */

    @Bean
    public FilterRegistrationBean addThreadNameFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        Filter myFilter = new CorrelatedThreadName();
        registration.setFilter(myFilter);
        registration.addUrlPatterns("/*");
        return registration;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTosURL() {
        return tosURL;
    }

    public void setTosURL(String tosURL) {
        this.tosURL = tosURL;
    }

    public Contact getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(Contact contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
