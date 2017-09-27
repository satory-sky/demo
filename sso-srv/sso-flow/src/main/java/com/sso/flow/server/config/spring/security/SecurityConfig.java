package com.sso.flow.server.config.spring.security;

import com.sso.flow.server.config.spring.security.auth.DaoTokenAuthenticationProvider;
import com.sso.flow.server.config.spring.security.auth.TokenSecurityContextRepository;
import com.sso.common.server.enums.UserRoleType;
import org.apache.commons.configuration.CompositeConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.token.KeyBasedPersistenceTokenService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.ldap.authentication.BindAuthenticator;
import org.springframework.security.ldap.authentication.LdapAuthenticationProvider;
import org.springframework.security.ldap.authentication.UserDetailsServiceLdapAuthoritiesPopulator;
import org.springframework.security.ldap.search.FilterBasedLdapUserSearch;

import java.security.SecureRandom;

/**
 * @author Original Author: Alexander Kontarero
 * @version 9/2/2014
 * @see © 2014 SSO - All Rights Reserved
 * See LICENSE file for further details
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private static final String LDAP_SERVER_URL = "ldap.server.url";
    private static final String LDAP_SERVER_PORT = "ldap.server.port";
    private static final String LDAP_SERVER_BIND_USER = "ldap.server.bind.user";
    private static final String LDAP_SERVER_BIND_PASSWORD = "ldap.server.bind.password";
    private static final String LDAP_SERVER_SEARCH_BASE = "ldap.server.search.base";
    private static final String LDAP_SERVER_SEARCH_FILTER = "ldap.server.search.filter";

    private static final String TOKEN_SECRET = "token.server.secret";
    private static final String TOKEN_INTEGER = "token.server.integer";
    private static final String PSEUDO_RANDOM_NUMBER_BYTES = "token.server.pseudo.random.number.bytes";

    private static final String PROTECTED_API_PATH = "/api";

    @Autowired
    CompositeConfiguration compositeConfiguration;
    @Autowired
    UserDetailsService userDetailsService;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            // defence from CSRF attack
            .csrf().disable()
            // this entry point handles when you request a protected page and
            // you are not yet authenticated
            .exceptionHandling().authenticationEntryPoint(entryPoint()).and()

//            .anonymous().and()
//            .servletApi().and()
//            .headers().cacheControl().and()

            // request security policy
            .authorizeRequests()
                // Allow anonymous resource requests
                .antMatchers("/", "/home", "/resources/**").permitAll()
                .antMatchers(PROTECTED_API_PATH).hasAnyAuthority(
//            UserRoleType.USER.name(),
//            UserRoleType.ADMIN.name(),
//            UserRoleType.DEPOT_ENGINEER.name(),
//            UserRoleType.CHIEF_DEPOT_ENGINEER.name(),
//            UserRoleType.BRANCH_ENGINEER.name(),
//            UserRoleType.CHIEF_BRANCH_ENGINEER.name(),
//            UserRoleType.MANAGEMENT_OF_TECHNICAL_POLICY_EXPERT.name(),
//            UserRoleType.MANAGEMENT_OF_TECHNICAL_POLICY_HEAD.name(),
//            UserRoleType.USER_CZDR.name(),
//            UserRoleType.ADMINISTRATOR.name(),
//            UserRoleType.ADMINISTRATOR_NSI.name(),
            UserRoleType.SUPER_ADMINISTRATOR.name())

//                .antMatchers(HttpMethod.PUT, "/api/V1/plan/extend").hasAnyAuthority(
//                    UserRoleType.DEPOT_ENGINEER.name(),
//                    UserRoleType.CHIEF_DEPOT_ENGINEER.name())
//                .antMatchers(HttpMethod.PUT, "/api/V1/plan/waitforpreapprove").hasAnyAuthority(
//                    UserRoleType.DEPOT_ENGINEER.name(),
//                    UserRoleType.BRANCH_ENGINEER.name(),
//                    UserRoleType.MANAGEMENT_OF_TECHNICAL_POLICY_EXPERT.name())
//                .antMatchers(HttpMethod.PUT, "/api/V1/plan/waitforapprove").hasAnyAuthority(
//                    UserRoleType.CHIEF_DEPOT_ENGINEER.name(),
//                    UserRoleType.CHIEF_BRANCH_ENGINEER.name(),
//                    UserRoleType.MANAGEMENT_OF_TECHNICAL_POLICY_HEAD.name())
//                .antMatchers(HttpMethod.PUT, "/api/V1/plan/approved").hasAnyAuthority(
//                    UserRoleType.BRANCH_ENGINEER.name(),
//                    UserRoleType.MANAGEMENT_OF_TECHNICAL_POLICY_EXPERT.name())
//                .antMatchers(HttpMethod.PUT, "/api/V1/plan/complete").hasAnyAuthority(
//                    UserRoleType.MANAGEMENT_OF_TECHNICAL_POLICY_HEAD.name())

            .anyRequest().authenticated()
//            .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and().securityContext().securityContextRepository(securityContextRepository());

        //Configures form login
        http.formLogin()
//            .loginPage("/loginpage")
            .loginProcessingUrl("/session")
//            .defaultSuccessUrl("/session?auth=success")
//            .failureUrl("/session?auth=failure")
            .successHandler(authenticationSuccessHandler())
            .failureHandler(authenticationFailureHandler())
            .usernameParameter("j_username")
            .passwordParameter("j_password")
            .permitAll();

        //Configures the logout function
        http.logout()
            .logoutUrl("/logout")
//            .logoutSuccessUrl("/logout/success")
            .logoutSuccessHandler(logoutSuccessHandler())
            // make session invalid after logout
            .deleteCookies("JSESSIONID")
//            .invalidateHttpSession(true)
            .permitAll();
    }

    @Bean
    public CustomEntryPoint entryPoint() {
      return new CustomEntryPoint();
    }

    @Bean
    public CustomAuthenticationSuccessHandler authenticationSuccessHandler() {
        CustomAuthenticationSuccessHandler authenticationSuccessHandler = new CustomAuthenticationSuccessHandler();
//        authenticationSuccessHandler.setTargetUrlParameter("/sso-flow/session?auth=success");
        return authenticationSuccessHandler;
    }

    @Bean
    public CustomAuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

    @Bean
    public CustomLogoutSuccessHandler logoutSuccessHandler() {
        return new CustomLogoutSuccessHandler();
    }

    @Bean
    public TokenSecurityContextRepository securityContextRepository() throws Exception {
        return new TokenSecurityContextRepository(authenticationManagerBean());
    }

    @Bean
    public KeyBasedPersistenceTokenService keyBasedPersistenceTokenService() {
        KeyBasedPersistenceTokenService keyBasedPersistenceTokenService =
            new KeyBasedPersistenceTokenService();
        keyBasedPersistenceTokenService.setPseudoRandomNumberBytes(
            compositeConfiguration.getInt(PSEUDO_RANDOM_NUMBER_BYTES));
        keyBasedPersistenceTokenService.setSecureRandom(secureRandom());
        keyBasedPersistenceTokenService.setServerSecret(compositeConfiguration.getString(TOKEN_SECRET));
        keyBasedPersistenceTokenService.setServerInteger(compositeConfiguration.getInt(TOKEN_INTEGER));
        return keyBasedPersistenceTokenService;
    }

    @Bean
    public SecureRandom secureRandom() {
        return new SecureRandom();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder authManagerBuilder) throws Exception {
        authManagerBuilder
            // register custom LDAP Provider
// TODO            .authenticationProvider(ldapAuthProvider()).eraseCredentials(false)
            // alternative Dao provider
            .authenticationProvider(daoTokenAuthenticationProvider()).eraseCredentials(false);
    }


    // LDAP provider
    @Bean
    public LdapAuthenticationProvider ldapAuthProvider() {
        return new LdapAuthenticationProvider(
            bindAuthenticator(),
            userDetailsServiceLdapAuthoritiesPopulator());
    }

    @Bean
    public UserDetailsServiceLdapAuthoritiesPopulator userDetailsServiceLdapAuthoritiesPopulator() {
        // register our own custom realization of UserDetailsService
        return new UserDetailsServiceLdapAuthoritiesPopulator(userDetailsService);
    }

    @Bean
    public BindAuthenticator bindAuthenticator() {
        BindAuthenticator bindAuthenticator = new BindAuthenticator(ldapContextSource());
        bindAuthenticator.setUserSearch(filterBasedLdapUserSearch());

        return bindAuthenticator;
    }

    @Bean
    LdapContextSource ldapContextSource() {
         LdapContextSource ldapContextSource = new LdapContextSource();
         ldapContextSource.setUrl(
             "ldap://" +
                 compositeConfiguration.getString(LDAP_SERVER_URL) +
                 ":" +
                 compositeConfiguration.getString(LDAP_SERVER_PORT) +
                 "/");
         ldapContextSource.setUserDn(
             compositeConfiguration.getString(LDAP_SERVER_BIND_USER));
         ldapContextSource.setPassword(
             compositeConfiguration.getString(LDAP_SERVER_BIND_PASSWORD));

         return ldapContextSource;
     }

    @Bean
    FilterBasedLdapUserSearch filterBasedLdapUserSearch() {
        return new FilterBasedLdapUserSearch(
            compositeConfiguration.getString(LDAP_SERVER_SEARCH_BASE),
            compositeConfiguration.getString(LDAP_SERVER_SEARCH_FILTER),
            ldapContextSource());
    }


    // Dao Token provider
    @Bean
    public DaoTokenAuthenticationProvider daoTokenAuthenticationProvider() {
        DaoTokenAuthenticationProvider daoTokenAuthenticationProvider = new DaoTokenAuthenticationProvider();
        // register our own custom realization of UserDetailsService
        daoTokenAuthenticationProvider.setUserDetailsService(userDetailsService);
        // and PasswordEncoder for password SHA1 encoding
        daoTokenAuthenticationProvider.setPasswordEncoder(shaPasswordEncoder());

        return daoTokenAuthenticationProvider;
    }

    @Bean
    public ShaPasswordEncoder shaPasswordEncoder(){
        return new ShaPasswordEncoder();
    }
}