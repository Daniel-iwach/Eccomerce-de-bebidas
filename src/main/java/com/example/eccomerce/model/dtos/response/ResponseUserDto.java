package com.example.eccomerce.model.dtos.response;

import com.example.eccomerce.model.enums.ERole;
import com.example.eccomerce.model.enums.EUserState;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record ResponseUserDto(
        String id,
        @NotBlank
        String name,
        @NotBlank
        String email,
        @NotBlank
        String address,
        @NotNull
        int numberPhone,
        Set<ERole> roleList,
        EUserState state
) {
}
