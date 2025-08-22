package com.example.eccomerce.service.impl;

import com.example.eccomerce.mappers.SaleDetailsMapper;
import com.example.eccomerce.model.SaleDetails;
import com.example.eccomerce.model.dtos.response.ResponseItemCartDto;
import com.example.eccomerce.model.dtos.response.ResponseMostSoldProductDto;
import com.example.eccomerce.repository.SaleDetailsRepository;
import com.example.eccomerce.service.interfaces.IItemCartService;
import com.example.eccomerce.service.interfaces.ISaleDetailsService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.bson.Document;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleDetailsServiceImpl implements ISaleDetailsService {
    private final SaleDetailsRepository saleDetailsRepository;
    private final SaleDetailsMapper saleDetailsMapper;
    private final IItemCartService itemCartService;

    @Override
    public List<String> createSaleDetails(String saleId, String cartId) {
        //BUSCA LOS ITEMS POR ID DEL CARRITO
        List<ResponseItemCartDto> itemCartDtoList=itemCartService.getByCartId(cartId);
        List<String> saleDetailsIds = new ArrayList<>();

        //MAPEA LOS ITEMS A DETALLES Y LOS GUARDA
        itemCartDtoList.forEach(item->{
            SaleDetails saleDetails=saleDetailsMapper.itemDtoToSaleDetails(item);
            saleDetails.setSaleId(new ObjectId(saleId));
            System.out.println(saleDetails);

            SaleDetails saleSaved =saleDetailsRepository.save(saleDetails);

            //AGREGA EL ID A LA LISTA
            saleDetailsIds.add(saleSaved.getId());

        });
        itemCartService.deleteAllItemsByCartId(cartId);
        return saleDetailsIds;
    }

    @Override
    public List<ResponseMostSoldProductDto> findMostSoldProducts() {
        return saleDetailsRepository.findMostSoldProduct();
    }

    @Override
    public List<ResponseMostSoldProductDto> findLeastSoldProduct() {
        return saleDetailsRepository.findLeastSoldProduct();
    }
}

