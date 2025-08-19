package com.example.eccomerce.service.interfaces;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ISaleDetailsService {
    List<String> createSaleDetails(String saleId, String cartId);
    void findMostSoldProducts();
    void findLeastSoldProduct();
}
