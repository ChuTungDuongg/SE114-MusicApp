package com.music.application.be.modules.auth.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;

}

