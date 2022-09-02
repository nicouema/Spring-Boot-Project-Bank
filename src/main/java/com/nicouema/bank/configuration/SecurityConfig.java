package com.nicouema.bank.configuration;

import com.nicouema.bank.common.exception.handler.AuthenticationEntryPointHandler;
import com.nicouema.bank.common.exception.handler.CustomAccessDeniedHandler;
import com.nicouema.bank.common.security.JwtRequestFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AnonymousAuthenticationProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.nicouema.bank.ports.input.rs.api.ApiConstants.*;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtRequestFilter jwtRequestFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .authorizeRequests()
//               >> DOCUMENTATION && LOGIN, REGISTER
                .antMatchers("/api/docs/**", "/api/swagger-ui/**", "/v3/api-docs/**", "/auth/login", "/auth/register").permitAll()
//               >> ADMIN:: DOCUMENT-TYPES && MOVEMENT_TYPES && BRANCHES && DELETE OPERATIONS
                .antMatchers(HttpMethod.DELETE, "/**").hasRole("ADMIN")
                .antMatchers(DOCUMENT_TYPE_URI + "/**").hasRole("ADMIN")
                .antMatchers(MOVEMENT_TYPE_URI + "/**").hasRole("ADMIN")
                .antMatchers(BRANCH_URI + "/**").hasRole("ADMIN")
                .antMatchers(CITY_URI + "/**").hasRole("ADMIN")
                .antMatchers(DOWNLOAD_URI + "/**").permitAll()
//               >> ACCOUNTS && STATEMENTS
                .antMatchers(HttpMethod.GET, ACCOUNT_URI).hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, ACCOUNT_URI).authenticated()
                .antMatchers(HttpMethod.PATCH, BANK_STATEMENT_URI + "/**").hasRole("ADMIN")
                .antMatchers(BANK_STATEMENT_URI + "/**").authenticated()
//               >> CLIENTS
                .antMatchers(HttpMethod.POST, CLIENT_URI).authenticated()
                .antMatchers(CLIENT_URI).hasRole("ADMIN")
                .antMatchers(HttpMethod.PATCH, CLIENT_URI + "/*").authenticated()
                .antMatchers(CLIENT_URI + "/my-accounts").authenticated()
                .antMatchers(CLIENT_URI + "/me").authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().exceptionHandling()
                .authenticationEntryPoint(new AuthenticationEntryPointHandler())
                .accessDeniedHandler(new CustomAccessDeniedHandler())
                .and().addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
        throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
