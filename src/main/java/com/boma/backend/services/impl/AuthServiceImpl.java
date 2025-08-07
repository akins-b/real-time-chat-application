package com.boma.backend.services.impl;

import com.boma.backend.dto.request.LoginRequest;
import com.boma.backend.dto.request.RegistrationRequest;
import com.boma.backend.dto.response.AuthResponse;
import com.boma.backend.models.VerificationToken;
import com.boma.backend.models.Users;
import com.boma.backend.repositories.UserRepo;
import com.boma.backend.repositories.VerificationTokenRepo;
import com.boma.backend.security.JwtService;
import com.boma.backend.services.AuthService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final EmailServiceImpl emailService;
    private final UserRepo userRepo;
    private final AuthenticationProvider authenticationProvider;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    private final VerificationTokenRepo tokenRepo;
    private final JwtService jwtService;
    private final ImageUploadServiceImpl uploadService;

    @Override
    public AuthResponse register(RegistrationRequest request) {
        Users user = new Users();
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setRole(request.getRole());
        user.setVerified(request.isVerified());
        try {
            user.setProfilePictureUrl(uploadService.uploadImage(request.getImage()));
        }
        catch (IOException e){
            throw new IllegalArgumentException("Error uploading image: " + e.getMessage());
        }
        user.setCreatedAt(LocalDateTime.now());
        userRepo.save(user);

        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationToken.setCreatedAt(LocalDateTime.now());
        verificationToken.setExpiredAt(LocalDateTime.now().plusMinutes(15)); // 15 minutes from now
        tokenRepo.save(verificationToken);

        String verificationUrl = "http://localhost:8080/api/auth/verify?token=" + token;
        emailService.sendEmail(user.getEmail(), user.getFirstName(), user.getLastName(), verificationUrl);

        return new AuthResponse("An email has been sent for verification.");
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        Users user = userRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("User does not exist"));

        if(user.isVerified()){
            Authentication authentication = authenticationProvider.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

            if (authentication.isAuthenticated()){
                String token = jwtService.generateToken(user.getUsername());

                return new AuthResponse("Login successful", token);
            }
            else{
                return new AuthResponse("login failed", null);
            }
        }
        else {
            throw new IllegalArgumentException("This account is not verified");
        }
    }

    @Override
    public String verify(String token){
        VerificationToken verificationToken = tokenRepo.findByToken(token)
                .orElseThrow(()-> new EntityNotFoundException("Token does not exist"));

        if (verificationToken.getExpiredAt().isBefore(LocalDateTime.now())){
            throw new IllegalArgumentException("Token is expired");
        }

        Users user = verificationToken.getUser();

        if (user.isVerified()){
            throw new IllegalArgumentException("User already verified");
        }

        user.setVerified(true);
        userRepo.save(user);
        tokenRepo.delete(verificationToken);

        String jwtToken = jwtService.generateToken(user.getUsername());
        return jwtToken;
    }
}
