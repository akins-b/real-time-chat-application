package com.boma.backend.services;

import com.boma.backend.dto.request.LoginRequest;
import com.boma.backend.dto.request.RegistrationRequest;
import com.boma.backend.dto.response.AuthResponse;
import com.boma.backend.dto.response.UserResponssDTO;

public interface AuthService {
    AuthResponse register(RegistrationRequest request);
    AuthResponse login(LoginRequest request);
    String verify(String token);
}
