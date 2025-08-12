package com.example.eccomerce.mappers;

import com.example.eccomerce.model.SaleDetails;
import com.example.eccomerce.model.dtos.response.ResponseItemCartDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ObjectIdMapper.class)
public interface SaleDetailsMapper {
    @Mapping(target = "id", ignore = true)
    SaleDetails itemDtoToSaleDetails(ResponseItemCartDto itemCartDto);
}
