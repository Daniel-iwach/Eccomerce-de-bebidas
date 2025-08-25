package com.example.eccomerce.model.dtos.response;

import com.example.eccomerce.model.enums.ESaleState;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record SaleReportDTO(
        String saleId,
        String userName,
        String phoneNumber,
        String paymentState,
        ESaleState saleState,
        String paymentMethod,
        BigDecimal total,
        LocalDateTime saleDate
) {
}
