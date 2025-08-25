package com.example.eccomerce.controller;

import com.example.eccomerce.model.dtos.response.ResponseMostSoldProductDto;
import com.example.eccomerce.model.dtos.response.ResponseSaleDetailsDto;
import com.example.eccomerce.service.interfaces.ISaleDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sale-details")
@RequiredArgsConstructor
public class SaleDetailsController {
    private final ISaleDetailsService saleDetailsService;

    @GetMapping("/get-by-sale-id/{saleId}")
    public ResponseEntity<List<ResponseSaleDetailsDto>>getBySaleId(@PathVariable String saleId){
        return new ResponseEntity<>(saleDetailsService.findDetailsWithProductNameBySaleId(saleId),HttpStatus.OK);
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/find-most-sold-products")
    public ResponseEntity<List<ResponseMostSoldProductDto>> findMostSoldProducts(){
        return new ResponseEntity<>(saleDetailsService.findMostSoldProducts(), HttpStatus.OK);
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/find-least-sold-products")
    public ResponseEntity<List<ResponseMostSoldProductDto>> findLeastSoldProducts() {
        return new ResponseEntity<>(saleDetailsService.findLeastSoldProduct(), HttpStatus.OK);
    }
}
