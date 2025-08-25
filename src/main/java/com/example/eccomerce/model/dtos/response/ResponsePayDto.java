package com.example.eccomerce.model.dtos.response;

import org.bson.types.ObjectId;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ResponsePayDto(
        String id,
        ObjectId saleId,
        String payMpId,
        String state,
        String method,
        BigDecimal amount,
        LocalDateTime creationDate
) {
}
