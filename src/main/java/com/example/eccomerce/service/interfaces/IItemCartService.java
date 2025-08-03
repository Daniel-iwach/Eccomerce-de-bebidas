package com.example.eccomerce.service.interfaces;

import com.example.eccomerce.model.dtos.request.RequestCreateItemCartDto;
import com.example.eccomerce.model.dtos.response.ResponseItemCartDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IItemCartService {
    ResponseItemCartDto addItemCart(RequestCreateItemCartDto createItemCartDto);
    List<ResponseItemCartDto>getAll();
    List<ResponseItemCartDto> getByCartId(String cartId);
    String deleteItemCart(String itemId);
}
