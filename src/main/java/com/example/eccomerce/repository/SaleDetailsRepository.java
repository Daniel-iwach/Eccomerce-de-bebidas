package com.example.eccomerce.repository;

import com.example.eccomerce.model.SaleDetails;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleDetailsRepository extends MongoRepository<SaleDetails,String> {
    @Aggregation(pipeline = {
            "{ $group: { _id: '$productId', totalQuantity: { $sum: '$quantity' } } }",
            "{ $sort: { totalQuantity: -1 } }",
            "{ $limit: 3 }",
            "{ $lookup: { from: 'Products', localField: '_id', foreignField: '_id', as: 'product' } }",
            "{ $unwind: '$product' }"
    })
    List<Document> findMostSoldProduct();

    @Aggregation(pipeline = {
            "{ $group: { _id: '$productId', totalQuantity: { $sum: '$quantity' } } }",
            "{ $sort: { totalQuantity: 1 } }",
            "{ $limit: 3 }",
            "{ $lookup: { from: 'Products', localField: '_id', foreignField: '_id', as: 'product' } }",
            "{ $unwind: '$product' }"
    })
    List<Document> findLeastSoldProduct();
}
