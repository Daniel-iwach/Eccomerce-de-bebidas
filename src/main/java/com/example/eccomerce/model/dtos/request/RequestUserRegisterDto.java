package com.example.eccomerce.model.dtos.request;

import com.example.eccomerce.model.enums.ERole;
import com.example.eccomerce.model.enums.EUserState;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.Set;

public record RequestUserRegisterDto(
    @NotBlank
    String name,
    @NotBlank
    String email,
    @NotBlank
    String password,
    @NotBlank
    String address,
    @NotNull
    int numberPhone
){}
