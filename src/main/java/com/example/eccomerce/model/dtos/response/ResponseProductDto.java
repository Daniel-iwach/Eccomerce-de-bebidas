package com.example.eccomerce.model.dtos.response;

import com.example.eccomerce.model.enums.ECategory;
import com.example.eccomerce.model.enums.EProductState;

public record ResponseProductDto(
        String id,
        String name,
        String description,
        String brand,
        int price,
        int stock,
        String urlImagen,
        ECategory category,
        EProductState state
) {
}
