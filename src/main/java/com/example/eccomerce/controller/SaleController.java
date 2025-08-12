package com.example.eccomerce.controller;

import com.example.eccomerce.model.dtos.request.RequestCreateSaleDto;
import com.example.eccomerce.model.dtos.response.ResponseSaleDto;
import com.example.eccomerce.service.interfaces.ISaleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sale")
@RequiredArgsConstructor
public class SaleController {
    private final ISaleService saleService;

    @PostMapping("/create")
    public ResponseEntity<ResponseSaleDto>createSale(@RequestBody @Valid RequestCreateSaleDto createSaleDto){
        return new ResponseEntity<>(saleService.createSale(createSaleDto), HttpStatus.OK);
    }

    @GetMapping("/list-all")
    public ResponseEntity<List<ResponseSaleDto>>getAll(){
        return new ResponseEntity<>(saleService.listAll(),HttpStatus.OK);
    }
}
