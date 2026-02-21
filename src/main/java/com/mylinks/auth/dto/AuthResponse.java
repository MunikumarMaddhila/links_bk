package com.mylinks.auth.dto;

import com.mylinks.response.UserResponse;

import lombok.Data;

@Data
public class AuthResponse {
    private String token;

    private UserResponse user;
    public AuthResponse(String token,UserResponse user) {
        this.token = token;
        this.user = user;
    }
}
