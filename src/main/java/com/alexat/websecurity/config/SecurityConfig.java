package com.alexat.websecurity.config;


/*
@author   AlexAT
@project   websecurity
@class  SecurityConfig
@version  1.0.0
@since 09.04.2025 - 01.23
*/

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests( req -> req
                        .requestMatchers("/", "/index.html").permitAll()

                        // USER, ADMIN, SUPERADMIN — Read-only endpoints
                        .requestMatchers(HttpMethod.GET, "/api/v1/dishes").hasAnyRole("USER", "ADMIN", "SUPERADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/dishes/{id}").hasAnyRole("USER", "ADMIN", "SUPERADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/dishes/category/**").hasAnyRole("USER", "ADMIN", "SUPERADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/dishes/most-expensive").hasAnyRole("USER", "ADMIN", "SUPERADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/dishes/low-calorie/**").hasAnyRole("USER", "ADMIN", "SUPERADMIN")

                        // ADMIN + SUPERADMIN — Write access
                        .requestMatchers(HttpMethod.POST, "/api/v1/dishes").hasAnyRole("ADMIN", "SUPERADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/v1/dishes/many").hasAnyRole("ADMIN", "SUPERADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/dishes").hasAnyRole("ADMIN", "SUPERADMIN")

                        // SUPERADMIN only
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/dishes/{id}").hasAnyRole("SUPERADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/dishes/clear-all").hasRole("SUPERADMIN")

                        .anyRequest().authenticated())

                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {

        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN")
                .build();

        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder().encode("user"))
                .roles("USER")
                .build();

        UserDetails superadmin = User.builder()
                .username("superadmin")
                .password(passwordEncoder().encode("superadmin"))
                .roles("SUPERADMIN")
                .build();


        return new InMemoryUserDetailsManager(admin, user, superadmin);
    }
}