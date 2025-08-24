package com.example.eccomerce.model.dtos.request;

import org.bson.types.ObjectId;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RequestCreatePayDto(
        ObjectId saleId,
        BigDecimal amount
) {
}
