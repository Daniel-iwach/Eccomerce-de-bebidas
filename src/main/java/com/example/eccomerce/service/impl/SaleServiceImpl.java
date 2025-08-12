package com.example.eccomerce.service.impl;

import com.example.eccomerce.mappers.SaleMapper;
import com.example.eccomerce.model.Sale;
import com.example.eccomerce.model.dtos.request.RequestCreateSaleDto;
import com.example.eccomerce.model.dtos.response.ResponseCartDto;
import com.example.eccomerce.model.dtos.response.ResponseSaleDto;
import com.example.eccomerce.repository.SaleRepository;
import com.example.eccomerce.service.interfaces.ICartService;
import com.example.eccomerce.service.interfaces.ISaleDetailsService;
import com.example.eccomerce.service.interfaces.ISaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements ISaleService {
    private final SaleRepository saleRepository;
    private final SaleMapper saleMapper;
    private final ICartService cartService;
    private final ISaleDetailsService saleDetailsService;

    @Override
    public ResponseSaleDto createSale(RequestCreateSaleDto createSaleDto) {
        Sale sale= saleMapper.createSaleToSale(createSaleDto);
        sale=saleRepository.save(sale);
        System.out.println(sale);

        //OBTIENE CARTID Y CREA LOS SALEDETAILS OBTIENIENDO LA LISTA DE IDS
        String cartId=findCartByUserId(sale.getUserId().toHexString());
        List<String> idsList =saleDetailsService.createSaleDetails(sale.getId(),cartId);

        //SETEA LA LISTA Y GUARDA
        sale.setSaleDetailsList(idsList);
        Sale saleSaved=saleRepository.save(sale);

        return saleMapper.saleToSaleDto(saleSaved);
    }

    @Override
    public List<ResponseSaleDto> listAll() {
        return saleMapper.saleListToSaleDtoList(saleRepository.findAll());
    }

    private String findCartByUserId(String userId){
        return cartService.getCartByUserId(userId).id();
    }
}
