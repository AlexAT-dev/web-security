package com.alexat.websecurity.auth;


/*
@author   AlexAT
@project   websecurity
@class  AuthenticationRequest
@version  1.0.0
@since 17.04.2025 - 16.56
*/

import lombok.Data;
import lombok.NonNull;
@Data
public class AuthenticationRequest {
    private String email;
    private String password;
}
