package com.example.eccomerce.model.dtos.request;

import com.example.eccomerce.model.enums.ESaleState;
import org.bson.types.ObjectId;

import java.util.List;

public record RequestCreateSaleDto(
        String userId,
        String payId,
        Integer total
) {
}
