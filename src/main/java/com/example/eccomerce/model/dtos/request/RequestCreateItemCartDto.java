package com.example.eccomerce.model.dtos.request;

public record RequestCreateItemCartDto(
        String cartId,
        String productId,
        int quantity
) {
}
