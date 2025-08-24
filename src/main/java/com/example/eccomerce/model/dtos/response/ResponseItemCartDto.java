package com.example.eccomerce.model.dtos.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

public record ResponseItemCartDto(
        String id,
        String cartId,
        String productId,
        int quantity,
        BigDecimal subTotal
) {
}
