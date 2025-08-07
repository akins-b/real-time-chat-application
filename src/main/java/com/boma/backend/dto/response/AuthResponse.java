package com.boma.backend.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String message;
    private String token;

    public AuthResponse(String message){
        this.message = message;
    }
}
