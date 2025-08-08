package com.example.eccomerce.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ItemsCart")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemCart {
    @Id
    private String id;
    @NotBlank
    private ObjectId cartId;
    @NotBlank
    private ObjectId productId;
    @NotNull
    private int quantity;
    @NotNull
    private int subTotal;
}
