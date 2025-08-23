package com.example.eccomerce.model.dtos.response;

import com.example.eccomerce.model.enums.ECategory;
import com.example.eccomerce.model.enums.EProductState;

import java.math.BigDecimal;

public record ResponseProductDto(
        String id,
        String name,
        String description,
        String brand,
        BigDecimal price,
        int stock,
        String urlImagen,
        ECategory category,
        EProductState state
) {
}
