package com.alexat.websecurity.auth;


/*
@author   AlexAT
@project   websecurity
@class  AuthenticationResponse
@version  1.0.0
@since 17.04.2025 - 17.00
*/

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class AuthenticationResponse {
    private String token;
}
