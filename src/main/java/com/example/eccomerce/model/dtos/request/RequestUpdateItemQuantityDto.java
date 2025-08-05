package com.example.eccomerce.model.dtos.request;

public record RequestUpdateItemQuantityDto(
        String itemCartId,
        String cartId
) {
}
