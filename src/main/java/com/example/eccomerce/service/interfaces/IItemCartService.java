package com.example.eccomerce.service.interfaces;

import com.example.eccomerce.model.dtos.request.RequestAddItemCartDto;
import com.example.eccomerce.model.dtos.request.RequestUpdateItemQuantityDto;
import com.example.eccomerce.model.dtos.response.ItemWithProductInfoDto;
import com.example.eccomerce.model.dtos.response.ResponseItemCartDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IItemCartService {
    ResponseItemCartDto addItemCart(RequestAddItemCartDto addItemCartDto);
    ResponseItemCartDto updateItemQuantity(RequestUpdateItemQuantityDto updateItemQuantityDto, boolean decrement);
    List<ResponseItemCartDto>getAll();
    ResponseItemCartDto getById(String itemId);
    List<ResponseItemCartDto> getByCartId(String cartId);
    List<ItemWithProductInfoDto>getItemWithProductByCartId(String cartId);
    Integer getTotalItemsByCartId(String cartId);
    String deleteItemCart(String itemId);
    String deleteAllItemsByCartId(String cartId);
}
