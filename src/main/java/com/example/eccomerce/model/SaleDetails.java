package com.example.eccomerce.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "salesDetails")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleDetails {
    @Id
    private String id;
    private ObjectId saleId;
    private ObjectId productId;
    private int quantity;
    private int subTotal;
}
