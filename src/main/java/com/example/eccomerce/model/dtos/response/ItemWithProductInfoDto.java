package com.example.eccomerce.model.dtos.response;

public record ItemWithProductInfoDto(
        String id,
        String cartId,
        int quantity,
        int subTotal,
        String productName,
        int productPrice,
        String productImage,
        String productCategory
) {
}
