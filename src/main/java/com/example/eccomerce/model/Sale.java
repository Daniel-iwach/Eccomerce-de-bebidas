package com.example.eccomerce.model;

import com.example.eccomerce.model.enums.ESaleState;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "sales")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sale {
    @Id
    private String id;
    private ObjectId userId;
    private ObjectId payId;
    private BigDecimal total;
    private LocalDateTime dateTime;
    private List<String> saleDetailsList;
    private ESaleState state;

}
