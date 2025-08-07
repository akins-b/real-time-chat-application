package com.boma.backend.dto.request;

import lombok.Data;

@Data
public class VerificationRequest {
    private String email;
    private String verificationCode;
}
