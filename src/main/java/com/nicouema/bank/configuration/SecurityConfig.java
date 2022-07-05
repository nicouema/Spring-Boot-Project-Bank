package com.nicouema.bank.configuration;

import com.nicouema.bank.common.exception.handler.AuthenticationEntryPointHandler;
import com.nicouema.bank.common.exception.handler.CustomAccessDeniedHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests()
                .antMatchers("/auth/register", "/auth/login").permitAll()
                .antMatchers("/document-types/**").hasRole("ADMIN")
                .and().exceptionHandling()
                .authenticationEntryPoint(new AuthenticationEntryPointHandler())
                .accessDeniedHandler(new CustomAccessDeniedHandler());
        return http.build();
    }

    @Bean
    protected PasswordEncoder encoder(AuthenticationManagerBuilder auth) throws Exception {
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
        throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
