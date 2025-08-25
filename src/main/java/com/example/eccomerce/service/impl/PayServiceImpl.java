package com.example.eccomerce.service.impl;

import com.example.eccomerce.mappers.PayMapper;
import com.example.eccomerce.model.Pay;
import com.example.eccomerce.model.dtos.request.RequestCreatePayDto;
import com.example.eccomerce.model.dtos.response.ResponsePayDto;
import com.example.eccomerce.repository.PayRepository;
import com.example.eccomerce.service.interfaces.IPayService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PayServiceImpl implements IPayService {
    private final PayRepository payRepository;
    private final PayMapper payMapper;

    @Override
    public ResponsePayDto createPay(RequestCreatePayDto createPayDto) {
        Pay pay = payMapper.createPayDtoToPay(createPayDto);
        pay.setPayMpId("1234");
        pay.setState("pending");
        pay.setMethod("transfer");
        pay.setCreationDate(LocalDateTime.now());
        pay = payRepository.save(pay);
        System.out.println(pay);
        return payMapper.payToResponsePayDto(pay);
    }
}
