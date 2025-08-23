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

@Document(collection = "ItemsCart")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemCart {
    @Id
    private String id;

    private ObjectId cartId;

    private ObjectId productId;

    private int quantity;

    private BigDecimal subTotal;
}
