package com.example.eccomerce.model.dtos.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;


public record RequestUserRegisterDto(
    @NotBlank
    String name,

    @NotBlank
    String lastName,

    @NotBlank
    String email,

    @NotBlank
    String password,

    @NotNull
    LocalDate birthday,

    @NotBlank
    String address,

    @NotNull
    int numberPhone
){}
