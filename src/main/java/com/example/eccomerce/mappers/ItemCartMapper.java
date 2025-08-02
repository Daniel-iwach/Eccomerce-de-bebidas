package com.example.eccomerce.mappers;

import com.example.eccomerce.model.ItemCart;
import com.example.eccomerce.model.dtos.request.RequestCreateItemCartDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItemCartMapper {
        ItemCart createItemCartDtoToItemCart(RequestCreateItemCartDto createItemCartDto);
}
