package com.boma.backend.controllers;

import com.boma.backend.dto.request.LoginRequest;
import com.boma.backend.dto.request.RegistrationRequest;
import com.boma.backend.dto.response.AuthResponse;
import com.boma.backend.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService service;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(RegistrationRequest request){
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(LoginRequest request) {
        return ResponseEntity.ok(service.login(request));
    }

    @PostMapping("/verify")
    public ResponseEntity<AuthResponse> verify(@RequestParam String token){
        String jwtToken = service.verify(token);
        return ResponseEntity.ok(new AuthResponse("User verified successfully", jwtToken));
    }
}
