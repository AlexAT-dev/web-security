package com.alexat.websecurity.config;


/*
@author   AlexAT
@project   websecurity
@class  AuditorAwareImpl
@version  1.0.0
@since 09.04.2025 - 03.12
*/

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(System.getProperty("user.name"));
    }
}