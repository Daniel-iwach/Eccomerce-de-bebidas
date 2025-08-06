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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public ResponseProductDto createProduct(String name, String description, String brand, int price, ECategory category, MultipartFile file) throws IOException {
        //GUARDA LA FOTO Y OBTIENE LA URL
        String imageUrl = savePicture(file);

        //CREA EL PRODUCTO Y LO GUARDA
        Product product= new Product(null, name, description, brand, price, 0, imageUrl ,category,EProductState.DESACTIVADO );
        product=productRepository.save(product);
        System.out.println(product);

        //RETORNA
        return productMapper.ProductToProductDto(product);
    }

//    public ResponseProductDto create(RequestProductCreateDto productCreateDto){
//        Product product=productMapper.ProductCreateDtoToProduct(productCreateDto);
//
//        product.setStock(0);
//        product.setState(EProductState.DESACTIVADO);
//
//        product=productRepository.save(product);
//        System.out.println(product);
//        return productMapper.ProductToProductDto(product);
//    }

    @Override
    public ResponseProductDto findById(String produdctId) {
        Product product= productRepository.findById(produdctId)
                .orElseThrow(()-> new NoSuchElementException("product by id: "+ produdctId +" not found"));
        return productMapper.ProductToProductDto(product);
    }


    public List<ResponseProductDto> listAll(){
        List<Product>productList=productRepository.findAll();
        return  productMapper.ProductListToProductDtoList(productList);
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
