package com.example.eccomerce.mappers;

import com.example.eccomerce.model.Product;
import com.example.eccomerce.model.dtos.request.RequestProductCreateDto;
import com.example.eccomerce.model.dtos.response.ResponseProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "category", target = "category")
    Product ProductCreateDtoToProduct(RequestProductCreateDto productCreateDto);
    @Mapping(source = "category", target = "category")
    ResponseProductDto ProductToProductDto(Product product);
    //LIST
    @Mapping(source = "category", target = "category")
    List<Product> ProductCreateDtoListToProductList(List<RequestProductCreateDto> productCreateDtoList);
    @Mapping(source = "category", target = "category")
    List<ResponseProductDto> ProductListToProductDtoList(List<Product> product);
}
