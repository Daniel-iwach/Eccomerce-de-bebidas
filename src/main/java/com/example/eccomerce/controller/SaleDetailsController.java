package com.example.eccomerce.controller;

import com.example.eccomerce.service.interfaces.ISaleDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sale-details")
@RequiredArgsConstructor
public class SaleDetailsController {
    private final ISaleDetailsService saleDetailsService;
}
