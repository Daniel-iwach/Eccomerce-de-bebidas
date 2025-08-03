package com.example.eccomerce.service.interfaces;

import com.example.eccomerce.model.dtos.request.RequestProductCreateDto;
import com.example.eccomerce.model.dtos.response.ResponseProductDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IProductService {

    ResponseProductDto create(RequestProductCreateDto user);

    ResponseProductDto findById(String produdctId);

    List<ResponseProductDto> listAll();
}
