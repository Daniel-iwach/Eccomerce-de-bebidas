package com.example.eccomerce.mappers;

import com.example.eccomerce.model.Sale;
import com.example.eccomerce.model.dtos.request.RequestCreateSaleDto;
import com.example.eccomerce.model.dtos.response.ResponseSaleDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = ObjectIdMapper.class)
public interface SaleMapper {
    Sale createSaleToSale(RequestCreateSaleDto createSaleDto);
    ResponseSaleDto saleToSaleDto(Sale sale);
    List<ResponseSaleDto>saleListToSaleDtoList(List<Sale>saleList);
}
