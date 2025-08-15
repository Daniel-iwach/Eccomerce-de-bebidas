package com.example.eccomerce.controller;

import com.example.eccomerce.model.dtos.response.ResponseCartDto;
import com.example.eccomerce.service.interfaces.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final ICartService cartService;

    @GetMapping("/get-cart-by-user/{userId}")
    public ResponseEntity<ResponseCartDto>getByUserId(@PathVariable String userId){
        return new ResponseEntity<>(cartService.getCartByUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/get-cart/{cartId}")
    public ResponseEntity<ResponseCartDto>getCart(@PathVariable String cartId){
        return new ResponseEntity<>(cartService.getCart(cartId), HttpStatus.OK);
    }

}
