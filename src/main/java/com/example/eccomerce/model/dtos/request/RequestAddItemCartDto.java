package com.example.eccomerce.model.dtos.request;

public record RequestAddItemCartDto(
        String cartId,
        String productId,
        int quantity
) {
}
