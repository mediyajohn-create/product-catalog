package com.example.productCatalog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/**").authenticated()
                        .anyRequest().permitAll()
                )
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    UserDetailsService userDetailsService() {
        UserDetails user = User
                .withUsername("admin")
                .password("{noop}password")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }
}