package com.example.eccomerce.mappers;

import com.example.eccomerce.model.Pay;
import com.example.eccomerce.model.dtos.request.RequestCreatePayDto;
import com.example.eccomerce.model.dtos.response.ResponsePayDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PayMapper {

    Pay createPayDtoToPay(RequestCreatePayDto createPayDto);

    ResponsePayDto payToResponsePayDto(Pay pay);

}
