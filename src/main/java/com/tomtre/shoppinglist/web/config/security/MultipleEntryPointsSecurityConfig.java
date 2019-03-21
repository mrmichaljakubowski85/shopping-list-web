package com.tomtre.shoppinglist.web.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tomtre.shoppinglist.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class MultipleEntryPointsSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MultipleEntryPointsSecurityConfig(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }


    @Configuration
    @Order(1)
    public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

        private final ObjectMapper objectMapper;

        public ApiWebSecurityConfigurationAdapter(ObjectMapper objectMapper) {
            this.objectMapper = objectMapper;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/api/**")
                    .authorizeRequests()
                    .anyRequest().hasRole("USER")
                    .and()
                    .httpBasic()
                    .and()
                    .csrf().disable()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .exceptionHandling()
                    .authenticationEntryPoint(new ApiAuthenticationEntryPoint(objectMapper));
           }
    }

    @Configuration
    @Order(2)
    public static class FormLoginWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

        private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

        public FormLoginWebSecurityConfigurationAdapter(CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler) {
            this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    .antMatchers("/product/**").hasRole(SecurityRoles.USER)
                    .and()
                    .formLogin()
                    .loginPage("/login")
                    .loginProcessingUrl("/authenticateTheUser")
                    .successHandler(customAuthenticationSuccessHandler)
                    .permitAll()
                    .and()
                    .logout()
                    .permitAll()
                    .and()
                    .exceptionHandling()
                    .accessDeniedPage("/access-denied");
        }
    }

    @Bean
    ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    private DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }

}
