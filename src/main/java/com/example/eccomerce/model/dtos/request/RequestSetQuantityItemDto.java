package com.example.eccomerce.model.dtos.request;

public record RequestSetQuantityItemDto(
        String itemId,
        Integer quantity
) {
}
