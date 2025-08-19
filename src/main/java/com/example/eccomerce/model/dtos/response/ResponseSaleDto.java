package com.example.eccomerce.model.dtos.response;

import com.example.eccomerce.model.enums.ESaleState;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.List;

public record ResponseSaleDto(
        String id,
        ObjectId userId,
        ObjectId payId,
        Integer total,
        LocalDateTime dateTime,
        List<String>saleDetailsList,
        ESaleState state
) {
}
