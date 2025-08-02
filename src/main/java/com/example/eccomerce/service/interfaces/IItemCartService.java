package com.example.eccomerce.service.interfaces;

import com.example.eccomerce.model.dtos.request.RequestCreateItemCartDto;
import com.example.eccomerce.model.dtos.response.ResponseItemCartDto;
import org.springframework.stereotype.Service;

@Service
public interface IItemCartService {
    ResponseItemCartDto createItemCart(RequestCreateItemCartDto createItemCartDto);
}
