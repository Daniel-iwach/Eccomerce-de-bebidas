package com.example.eccomerce.service.impl;

import com.example.eccomerce.mappers.CartMapper;
import com.example.eccomerce.model.Cart;
import com.example.eccomerce.model.dtos.response.ResponseCartDto;
import com.example.eccomerce.repository.CartRepository;
import com.example.eccomerce.service.interfaces.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements ICartService {
    private final CartRepository cartRepository;
    private final CartMapper cartMapper;

    public ResponseCartDto createCart (String userId){
        Cart cart= new Cart(null,userId,new ArrayList<String>(),0);
        cart=cartRepository.save(cart);
        return cartMapper.cartToResponseCartDto(cart);
    }

    @Override
    public ResponseCartDto getCartByUserId(String userId) {
        Cart cart= cartRepository.findByUserId(userId)
                .orElseThrow(()->new NoSuchElementException("Cart with userId: "+userId+" not found"));
        System.out.println(cart);
        return cartMapper.cartToResponseCartDto(cart);
    }
}
