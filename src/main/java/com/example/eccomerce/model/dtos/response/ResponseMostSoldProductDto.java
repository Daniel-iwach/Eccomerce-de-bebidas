package com.example.eccomerce.model.dtos.response;

import com.example.eccomerce.model.Product;

public record ResponseMostSoldProductDto(
        String name,
        Integer totalQuantity
) {
}
