package com.example.eccomerce.model.dtos.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record RequestFindByDateTime(
        @NotNull(message = "La fecha de inicio no puede ser nula")
        LocalDateTime start,
        @NotNull(message = "La fecha de fin no puede ser nula")
        LocalDateTime end
) {
}
