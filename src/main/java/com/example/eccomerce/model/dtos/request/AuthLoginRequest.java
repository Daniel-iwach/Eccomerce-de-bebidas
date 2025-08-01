package com.example.eccomerce.model.dtos.request;

import jakarta.validation.constraints.NotBlank;

public record AuthLoginRequest(
        @NotBlank
        String email,
        @NotBlank
        String password) {
}
