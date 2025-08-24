package com.example.eccomerce.model.dtos.response;

import java.math.BigDecimal;

public record ResponseSaleDetailsDto(
        Integer quantity,
        BigDecimal subTotal,
        String productName
) {
}
