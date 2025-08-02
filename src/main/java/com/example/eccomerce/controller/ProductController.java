package com.example.eccomerce.controller;

import com.example.eccomerce.model.dtos.request.RequestProductCreateDto;
import com.example.eccomerce.model.dtos.response.ResponseProductDto;
import com.example.eccomerce.service.interfaces.IProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final IProductService productService;

    @PostMapping("/create")
    public ResponseEntity<ResponseProductDto> create(@RequestBody @Valid RequestProductCreateDto createProductDto) {
        return new ResponseEntity<>(productService.create(createProductDto), HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ResponseProductDto>>getAll(){
        return new ResponseEntity<>(productService.listAll(),HttpStatus.OK);
    }
}
