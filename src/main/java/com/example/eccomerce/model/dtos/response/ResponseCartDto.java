package com.example.eccomerce.model.dtos.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;

import java.util.List;

public record ResponseCartDto(
        String id,
        String userId,
        List<String>itemCartList,
        int total
) {
}
