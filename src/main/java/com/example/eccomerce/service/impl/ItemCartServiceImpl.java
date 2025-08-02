package com.example.eccomerce.service.impl;

import com.example.eccomerce.mappers.ItemCartMapper;
import com.example.eccomerce.model.ItemCart;
import com.example.eccomerce.model.dtos.request.RequestCreateItemCartDto;
import com.example.eccomerce.model.dtos.response.ResponseItemCartDto;
import com.example.eccomerce.repository.ItemCartRepository;
import com.example.eccomerce.service.interfaces.IItemCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemCartServiceImpl implements IItemCartService {
    private final ItemCartRepository cartRepository;
    private final ItemCartMapper itemCartMapper;

    @Override
    public ResponseItemCartDto createItemCart(RequestCreateItemCartDto createItemCartDto) {
        ItemCart itemCart=itemCartMapper.createItemCartDtoToItemCart(createItemCartDto);
        
    }
}
