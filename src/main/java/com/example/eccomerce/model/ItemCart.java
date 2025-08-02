package com.example.eccomerce.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    private String cartId;
    @NotBlank
    private String productId;
    @NotNull
    private int quantity;
    @NotNull
    private int subTotal;
}
