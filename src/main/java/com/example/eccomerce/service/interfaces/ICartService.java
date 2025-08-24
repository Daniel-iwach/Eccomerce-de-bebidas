package com.example.eccomerce.service.interfaces;

import com.example.eccomerce.model.dtos.response.ResponseCartDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public interface ICartService {
    ResponseCartDto createCart (String userId);
    ResponseCartDto getCartByUserId (String userId);
    ResponseCartDto getCart (String cartId);
    ResponseCartDto updateTotalPrice(String cartId, BigDecimal total);
    ResponseCartDto updateItemList(String cartId, String itemId, boolean toDelete);
    ResponseCartDto resetCart(String cartId);

}
