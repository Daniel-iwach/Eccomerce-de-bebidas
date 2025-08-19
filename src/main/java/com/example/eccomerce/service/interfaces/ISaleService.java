package com.example.eccomerce.service.interfaces;

import com.example.eccomerce.model.dtos.request.RequestCreateSaleDto;
import com.example.eccomerce.model.dtos.request.RequestFindByDateTime;
import com.example.eccomerce.model.dtos.response.ResponseSaleDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface ISaleService {
    ResponseSaleDto createSale(RequestCreateSaleDto createSaleDto);
    List<ResponseSaleDto>listAll();
    List<ResponseSaleDto>findByDateTime(RequestFindByDateTime requestFindByDateTime);
}
