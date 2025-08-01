package com.example.eccomerce.model.dtos.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonPropertyOrder({"message", "status", "jwt","roles"})
public record AuthResponse(
        String message,
        Boolean status,
        List<String> roles )  {
}
