package com.example.eccomerce.service.interfaces;

import com.example.eccomerce.model.dtos.request.RequestCreatePayDto;
import com.example.eccomerce.model.dtos.response.ResponsePayDto;
import org.springframework.stereotype.Service;

@Service
public interface IPayService {
    ResponsePayDto createPay(RequestCreatePayDto createPayDto);

}
