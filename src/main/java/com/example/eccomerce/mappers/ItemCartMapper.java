package com.example.eccomerce.mappers;

import com.example.eccomerce.model.ItemCart;
import com.example.eccomerce.model.dtos.request.RequestCreateItemCartDto;
import com.example.eccomerce.model.dtos.response.ResponseItemCartDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ItemCartMapper {
        ItemCart createItemCartDtoToItemCart(RequestCreateItemCartDto createItemCartDto);
        ResponseItemCartDto ItemCartToItemCartDto(ItemCart itemCart);
        List<ResponseItemCartDto> ItemCartListToItemCartDtoList(List<ItemCart> itemCartList);
}
