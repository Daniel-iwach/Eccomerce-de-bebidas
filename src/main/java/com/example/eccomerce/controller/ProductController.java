package com.example.eccomerce.controller;

import com.example.eccomerce.model.dtos.request.RequestProductCreateDto;
import com.example.eccomerce.model.dtos.response.ResponseProductDto;
import com.example.eccomerce.model.enums.ECategory;
import com.example.eccomerce.service.interfaces.IProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final IProductService productService;

//    @PostMapping("/create")
//    public ResponseEntity<ResponseProductDto> create(@RequestBody @Valid RequestProductCreateDto createProductDto) {
//        return new ResponseEntity<>(productService.create(createProductDto), HttpStatus.OK);
//    }

//    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> crearProducto(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("brand") String brand,
            @RequestParam("price") BigDecimal price,
            @RequestParam("category") ECategory category,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        return new ResponseEntity<>(productService.createProduct(name, description, brand, price, category, file),HttpStatus.OK);

    }

    @GetMapping("/get-all")
    public ResponseEntity<List<ResponseProductDto>>getAll(){
        return new ResponseEntity<>(productService.listAll(),HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/getById/{productId}")
    public ResponseEntity<ResponseProductDto>getById(@PathVariable String productId){
        return new ResponseEntity<>(productService.findById(productId),HttpStatus.OK);
    }

    @PutMapping("/change-state/{productId}")
    public ResponseEntity<String> changeStateById(@PathVariable String productId){
        return new ResponseEntity<>(productService.changeStateById(productId),HttpStatus.OK);
    }
}
