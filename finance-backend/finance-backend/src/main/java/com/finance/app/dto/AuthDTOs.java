package com.finance.app.dto;

import jakarta.validation.constraints.*;
import lombok.*;

public class AuthDTOs {

    @Data
    public static class RegisterRequest {
        @NotBlank private String username;
        @NotBlank @Email private String email;
        @NotBlank @Size(min = 6) private String password;
        private String fullName;
    }

    @Data
    public static class LoginRequest {
        @NotBlank private String email;
        @NotBlank private String password;
    }

    @Data
    @AllArgsConstructor
    public static class AuthResponse {
        private String token;
        private String username;
        private String email;
        private String fullName;
    }
}