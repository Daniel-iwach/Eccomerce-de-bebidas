package com.example.eccomerce.service.impl;

import com.example.eccomerce.mappers.ProductMapper;
import com.example.eccomerce.model.Product;
import com.example.eccomerce.model.dtos.request.RequestProductCreateDto;
import com.example.eccomerce.model.dtos.response.ResponseProductDto;
import com.example.eccomerce.model.enums.ECategory;
import com.example.eccomerce.model.enums.EProductState;
import com.example.eccomerce.repository.ProductRepository;
import com.example.eccomerce.service.interfaces.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public ResponseProductDto createProduct(String name, String description, String brand, BigDecimal price, ECategory category, MultipartFile file) throws IOException {
        //GUARDA LA FOTO Y OBTIENE LA URL
        String imageUrl = savePicture(file);

        //CREA EL PRODUCTO Y LO GUARDA
        Product product= new Product(null, name, description, brand, price, 0, imageUrl ,category,EProductState.DESACTIVADO );
        product=productRepository.save(product);
        System.out.println(product);

        //RETORNA
        return productMapper.ProductToProductDto(product);
    }

    @Override
    public ResponseProductDto updateProduct(String id, String name, String description, String brand, Integer newStock, BigDecimal price, ECategory category, MultipartFile file) throws IOException {
        Product product=productRepository.findById(id).
                 orElseThrow(()-> new NoSuchElementException("product by id: "+ id +" not found"));

        String imageUrl;
        if (file == null || file.isEmpty()) {
            imageUrl=product.getUrlImagen();
        }else {
            imageUrl = savePicture(file);
        }

        product.setName(name);
        product.setDescription(description);
        product.setBrand(brand);
        product.setStock(product.getStock()+newStock);
        product.setPrice(price);
        product.setCategory(category);
        product.setUrlImagen(imageUrl);

        product=productRepository.save(product);
        System.out.println(product);

        return productMapper.ProductToProductDto(product);

    }


    @Override
    public String changeStateById(String productId) {
        Product product= productRepository.findById(productId)
                .orElseThrow(()-> new NoSuchElementException("product by id: "+ productId +" not found"));
        if (product.getState().equals(EProductState.ACTIVADO)){
            product.setState(EProductState.DESACTIVADO);
        }else {
            product.setState(EProductState.ACTIVADO);
        }
        productRepository.save(product);
        return "estado actualizado";
    }


    @Override
    public ResponseProductDto findById(String productId) {
        Product product= productRepository.findById(productId)
                .orElseThrow(()-> new NoSuchElementException("product by id: "+ productId +" not found"));
        return productMapper.ProductToProductDto(product);
    }


    public List<ResponseProductDto> listAll(){
        List<Product>productList=productRepository.findAll();
        return  productMapper.ProductListToProductDtoList(productList);
    }

    @Override
    public Integer countByStock(int stock) {
        return (int) productRepository.countByStock(stock);
    }

    @Override
    public Integer countByStockLessThan(int stock) {
        return (int) productRepository.countByStockLessThan(stock);
    }

    private String savePicture(MultipartFile file) throws IOException{
        // Guardar la imagen
        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();

        // Ruta física fuera del classpath
        Path uploadDir = Paths.get("uploads/");
        Path path = uploadDir.resolve(filename);

        // Crear carpeta si no existe
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        // Guardar el archivo
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        // Generar URL accesible desde navegador
        String imageUrl = "/uploads/" + filename.replace("\\", "/");
        return imageUrl;
    }
}
