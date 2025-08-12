package com.example.eccomerce.service.interfaces;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ISaleDetailsService {
    List<String> createSaleDetails(String saleId, String cartId);
}
