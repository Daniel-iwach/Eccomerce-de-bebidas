package com.example.eccomerce.model;

import com.example.eccomerce.model.enums.EPayState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Document(collection = "payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pay {
    private String id;
    private ObjectId saleId;
    private String payMpId;
    private String state;
    private String method;
    private BigDecimal amount;
    private LocalDateTime creationDate;
    private LocalDateTime updateDate;

}
