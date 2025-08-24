package com.example.eccomerce.service.interfaces;


import com.example.eccomerce.model.dtos.response.ResponseMostSoldProductDto;
import com.example.eccomerce.model.dtos.response.ResponseSaleDetailsDto;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ISaleDetailsService {
    List<String> createSaleDetails(String saleId, String cartId);
    List<ResponseMostSoldProductDto> findMostSoldProducts();
    List<ResponseMostSoldProductDto> findLeastSoldProduct();
    List<ResponseSaleDetailsDto> findDetailsWithProductNameBySaleId(String saleId);
}
