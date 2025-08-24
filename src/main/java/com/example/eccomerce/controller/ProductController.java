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
    public ResponseEntity<?> createProduct(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("brand") String brand,
            @RequestParam("price") BigDecimal price,
            @RequestParam("category") ECategory category,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        if (name == null || name.isBlank()) {
            return ResponseEntity.badRequest().body("El nombre es obligatorio");
        }
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            return ResponseEntity.badRequest().body("El precio debe ser mayor a 0");
        }
        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body("Debe subir una imagen");
        }

        return new ResponseEntity<>(productService.createProduct(name, description, brand, price, category, file),HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateProduct(
            @RequestParam("id") String id,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("brand") String brand,
            @RequestParam("newStock")Integer newStock,
            @RequestParam("price") BigDecimal price,
            @RequestParam("category") ECategory category,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) throws IOException {
        if (name == null || name.isBlank()) {
            return ResponseEntity.badRequest().body("El nombre es obligatorio");
        }
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            return ResponseEntity.badRequest().body("El precio debe ser mayor a 0");
        }
        return new ResponseEntity<>(productService.updateProduct(id, name, description, brand, newStock, price, category, file),HttpStatus.OK);
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

    @GetMapping("/count-by-stock/{stock}")
    public ResponseEntity<Integer> countByStock(@PathVariable int stock){
        return new ResponseEntity<>(productService.countByStock(stock),HttpStatus.OK);
    }

    @GetMapping("/count-by-stock-less-than/{stock}")
    public ResponseEntity<Integer> countByStockLessThan(@PathVariable int stock){
        return new ResponseEntity<>(productService.countByStockLessThan(stock),HttpStatus.OK);
    }
}
