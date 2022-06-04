package com.example.sa.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class Login {
    @NotNull
    private String clientId;

    @NotNull
    private String clientSecret;

    private String name;
    
    public Login(String clientId, String  clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }
    
}
