package com.boma.backend.dto.request;

import com.boma.backend.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.apache.catalina.User;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
public class RegistrationRequest {
    @NotBlank(message = "username is required")
    private String username;

    @NotBlank(message = "email is required")
    private String email;

    @NotBlank(message = "password is required")
    private String password;

    @NotBlank(message = "first name is required")
    private String firstName;

    @NotBlank(message = "last name is required")
    private String lastName;

    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    @NotBlank(message = "role is required")
    private UserRole role;

    private MultipartFile image;

    private boolean isVerified = false;

    private LocalDateTime createdAt = LocalDateTime.now();


}
