package com.example.eccomerce.controller;

import com.example.eccomerce.service.interfaces.ISaleDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sale-details")
@RequiredArgsConstructor
public class SaleDetailsController {
    private final ISaleDetailsService saleDetailsService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/find-most-sold-products")
    public ResponseEntity<String> findMostSoldProducts(){
        saleDetailsService.findMostSoldProducts();
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/find-least-sold-products")
    public ResponseEntity<String> findLeastSoldProducts() {
        saleDetailsService.findLeastSoldProduct();
        return new ResponseEntity<>("", HttpStatus.OK);
    }
}
