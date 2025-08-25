package com.example.eccomerce.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

@Document(collection = "carts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    @Id
    private String id;
    @NotBlank
    private ObjectId userId;
    private List<String> itemCartList;
    @NotNull
    private BigDecimal total;
}
