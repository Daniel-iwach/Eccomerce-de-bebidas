package com.example.eccomerce.service.impl;

import com.example.eccomerce.mappers.CartMapper;
import com.example.eccomerce.model.Cart;
import com.example.eccomerce.model.dtos.response.ResponseCartDto;
import com.example.eccomerce.model.dtos.response.ResponseUserDto;
import com.example.eccomerce.repository.CartRepository;
import com.example.eccomerce.service.interfaces.ICartService;
import com.example.eccomerce.service.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements ICartService {
    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final MongoTemplate mongoTemplate;

    public ResponseCartDto createCart (String userId){
        Cart cart= new Cart(null,new ObjectId(userId),new ArrayList<String>(), BigDecimal.valueOf(0));
        cart=cartRepository.save(cart);
        return cartMapper.cartToResponseCartDto(cart);
    }

    @Override
    public ResponseCartDto getCartByUserId(String userId) {
        Cart cart= cartRepository.findByUserId(new ObjectId(userId))
                .orElseThrow(()->new NoSuchElementException("Cart with userId: "+userId+" not found"));
        return cartMapper.cartToResponseCartDto(cart);
    }

    @Override
    public ResponseCartDto getCart(String cartId) {
        Cart cart= cartRepository.findById(cartId)
                .orElseThrow(()->new NoSuchElementException("Cart with cartId: "+cartId+" not found"));
        return cartMapper.cartToResponseCartDto(cart);
    }

    @Override
    public ResponseCartDto updateTotalPrice(String cartId, BigDecimal total) {
        Cart cart=cartRepository.findById(cartId)
                .orElseThrow(()->new NoSuchElementException("Cart with Id: "+cartId+" not found"));
        cart.setTotal(total);
        cart=cartRepository.save(cart);
        return cartMapper.cartToResponseCartDto(cart);
    }

    @Override
    public ResponseCartDto updateItemList(String cartId, String itemId, boolean toDelete) {
        Cart cart=cartRepository.findById(cartId)
                .orElseThrow(()->new NoSuchElementException("Cart with Id: "+cartId+" not found"));
        List<String> itemList= cart.getItemCartList();
        if (!toDelete){
            itemList.add(itemId);
        }else {
            itemList.remove(itemId);
        }
        cart.setItemCartList(itemList);
        cart=cartRepository.save(cart);
        return cartMapper.cartToResponseCartDto(cart);
    }

    @Override
    public ResponseCartDto resetCart(String cartId) {
        Cart cart=cartRepository.findById(cartId)
                .orElseThrow(()->new NoSuchElementException("Cart with Id: "+cartId+" not found"));
        cart.setItemCartList(new ArrayList<>());
        cart.setTotal(BigDecimal.valueOf(0));
        cart=cartRepository.save(cart);
        return cartMapper.cartToResponseCartDto(cart);
    }


}
