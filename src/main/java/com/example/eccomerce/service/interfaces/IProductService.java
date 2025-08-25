package com.example.eccomerce.service.interfaces;

import com.example.eccomerce.model.dtos.request.RequestProductCreateDto;
import com.example.eccomerce.model.dtos.response.ResponseProductDto;
import com.example.eccomerce.model.enums.ECategory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Service
public interface IProductService {
    ResponseProductDto createProduct(String name, String description, String brand,
                                     BigDecimal price, ECategory category, MultipartFile file) throws IOException;

    ResponseProductDto updateProduct(String id,String name, String description, String brand, Integer newStock,
                                     BigDecimal price, ECategory category, MultipartFile file) throws IOException;

    //ResponseProductDto create(RequestProductCreateDto user);

    String changeStateById(String productId);

    ResponseProductDto findById(String produdctId);

    List<ResponseProductDto> listAll();

    Integer countByStock(int stock);

    Integer countByStockLessThan(int stock);
}
