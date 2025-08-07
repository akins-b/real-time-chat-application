package com.boma.backend.services;

public interface EmailService {
    void sendEmail(String toEmail, String firstName, String lastName, String url);
}
