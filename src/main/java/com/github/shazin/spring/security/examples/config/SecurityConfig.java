package com.github.shazin.spring.security.examples.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.DelegatingLogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.accept.ParameterContentNegotiationStrategy;

import java.util.Collections;
import java.util.LinkedHashMap;

/**
 * Created by shazi on 4/25/2016.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/failure")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .successForwardUrl("/success")
                .failureForwardUrl("/failure")
                .and()
                .logout()
                .logoutSuccessHandler(logoutSuccessHandler());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .passwordEncoder(passwordEncoder())
                .withUser("user")
                // password
                .password("$e0801$RiCQjVv5oUECSTLsoFAocOraIHlkz+7jFyYDiOZvhGELYubSU2+6nOqHEn5Di+4vVYTCu9/iehEz3hTMX/BPAA==$dyfPzvED9YX2VPHkgIz+qRR3Vxku3Sz6gXab25w3m30=")
                .roles("USER");
    }

    @Bean
    public SCryptPasswordEncoder passwordEncoder() {
        SCryptPasswordEncoder sCryptPasswordEncoder = new SCryptPasswordEncoder();
        return sCryptPasswordEncoder;
    }

    @Bean
    public DelegatingLogoutSuccessHandler logoutSuccessHandler() {
        LinkedHashMap<RequestMatcher, LogoutSuccessHandler> matcherToHandler = new LinkedHashMap<RequestMatcher, LogoutSuccessHandler>();
        matcherToHandler.put(new MediaTypeRequestMatcher(new ParameterContentNegotiationStrategy(Collections.singletonMap("json", MediaType.APPLICATION_JSON)), MediaType.APPLICATION_JSON), new HttpStatusReturningLogoutSuccessHandler(HttpStatus.FOUND));
        DelegatingLogoutSuccessHandler delegatingLogoutSuccessHandler = new DelegatingLogoutSuccessHandler(matcherToHandler);
        delegatingLogoutSuccessHandler.setDefaultLogoutSuccessHandler(new SimpleUrlLogoutSuccessHandler());

        return delegatingLogoutSuccessHandler;
    }
}
