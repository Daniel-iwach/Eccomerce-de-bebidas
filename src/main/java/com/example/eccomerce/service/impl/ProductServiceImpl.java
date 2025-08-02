package com.example.eccomerce.service.impl;

import com.example.eccomerce.mappers.ProductMapper;
import com.example.eccomerce.model.Product;
import com.example.eccomerce.model.dtos.request.RequestProductCreateDto;
import com.example.eccomerce.model.dtos.response.ResponseProductDto;
import com.example.eccomerce.model.enums.EProductState;
import com.example.eccomerce.repository.ProductRepository;
import com.example.eccomerce.service.interfaces.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ResponseProductDto create(RequestProductCreateDto productCreateDto){
        Product product=productMapper.ProductCreateDtoToProduct(productCreateDto);

        product.setStock(0);
        product.setState(EProductState.DESACTIVADO);

        product=productRepository.save(product);
        System.out.println(product);
        return productMapper.ProductToProductDto(product);
    }


    public List<ResponseProductDto> listAll(){
        List<Product>productList=productRepository.findAll();
        return  productMapper.ProductListToProductDtoList(productList);
    }
}
