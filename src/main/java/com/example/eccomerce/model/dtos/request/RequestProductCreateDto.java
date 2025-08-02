package com.example.eccomerce.model.dtos.request;

import com.example.eccomerce.model.enums.ECategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RequestProductCreateDto(
        @NotBlank
        String name,
        @NotBlank
        String description,
        @NotBlank
        String brand,
        @NotNull
        int price,
        @NotBlank
        String urlImagen,
        @NotNull
        ECategory category
) {
}
