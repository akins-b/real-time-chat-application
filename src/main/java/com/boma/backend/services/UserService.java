package com.boma.backend.services;

import com.boma.backend.dto.request.RegistrationRequest;
import com.boma.backend.dto.request.VerificationRequest;
import com.boma.backend.dto.response.UserResponssDTO;

public interface UserService {
    UserResponssDTO createUser(RegistrationRequest request);
    void verifyEmail(VerificationRequest request);
}
