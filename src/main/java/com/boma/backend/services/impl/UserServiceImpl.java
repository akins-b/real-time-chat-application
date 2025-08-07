package com.boma.backend.services.impl;

import com.boma.backend.dto.request.RegistrationRequest;
import com.boma.backend.dto.request.VerificationRequest;
import com.boma.backend.dto.response.UserResponssDTO;
import com.boma.backend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final EmailServiceImpl emailService;

    @Override
    public UserResponssDTO createUser(RegistrationRequest request){
        return null;
    }
    @Override
    public void verifyEmail(VerificationRequest request) {
    }
}
