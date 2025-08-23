package com.example.eccomerce.model;

import com.example.eccomerce.model.enums.ECategory;
import com.example.eccomerce.model.enums.EProductState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document(collection = "Products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    private String id;
    private String name;
    private String description;
    private String brand;
    private BigDecimal price;
    private int stock;
    private String urlImagen;
    private ECategory category;
    private EProductState state;
}
