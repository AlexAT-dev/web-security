package com.alexat.websecurity.config;


/*
@author   AlexAT
@project   websecurity
@class  AuditionConfig
@version  1.0.0
@since 09.04.2025 - 03.15
*/

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@EnableMongoAuditing
@Configuration
public class AuditionConfig {

    @Bean
    public AuditorAware<String> auditorAware() {
        return new AuditorAwareImpl();
    }

}