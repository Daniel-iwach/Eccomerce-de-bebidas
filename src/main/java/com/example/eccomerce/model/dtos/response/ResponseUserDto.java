package com.example.eccomerce.model.dtos.response;

import com.example.eccomerce.model.enums.ERole;
import com.example.eccomerce.model.enums.EUserState;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

public record ResponseUserDto(
        String id,
        String name,
        String lastName,
        String email,
        LocalDate birthday,
        Date registerDate,
        String address,
        int numberPhone,
        Set<ERole> roleList,
        EUserState state
) {
}
