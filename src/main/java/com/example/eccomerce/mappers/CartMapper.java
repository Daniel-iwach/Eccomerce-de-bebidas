package com.example.eccomerce.mappers;

import com.example.eccomerce.model.Cart;
import com.example.eccomerce.model.dtos.response.ResponseCartDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ObjectIdMapper.class)
public interface CartMapper {
    @Mapping(source = "id", target = "id")
    ResponseCartDto cartToResponseCartDto(Cart cart);
}
