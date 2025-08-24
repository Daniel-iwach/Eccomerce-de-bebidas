package com.example.eccomerce.repository;

import com.example.eccomerce.model.SaleDetails;
import com.example.eccomerce.model.dtos.response.ResponseMostSoldProductDto;
import com.example.eccomerce.model.dtos.response.ResponseSaleDetailsDto;
import org.bson.Document;
import org.bson.types.ObjectId;
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
            "{ $unwind: '$product' }",
            "{ $project: { _id: 0, name: '$product.name', totalQuantity: 1 } }"
    })
    List<ResponseMostSoldProductDto> findMostSoldProduct();

    @Aggregation(pipeline = {
            "{ $group: { _id: '$productId', totalQuantity: { $sum: '$quantity' } } }",
            "{ $sort: { totalQuantity: 1 } }",
            "{ $limit: 3 }",
            "{ $lookup: { from: 'Products', localField: '_id', foreignField: '_id', as: 'product' } }",
            "{ $unwind: '$product' }",
            "{ $project: { _id: 0, name: '$product.name', totalQuantity: 1 } }"
    })
    List<ResponseMostSoldProductDto> findLeastSoldProduct();

    @Aggregation(pipeline = {
            "{ $match: { saleId: ?0 } }",
            "{ $lookup: { from: 'Products', localField: 'productId', foreignField: '_id', as: 'product' } }",
            "{ $unwind: '$product' }",
            "{ $project: { quantity: 1, subTotal: 1, productName: '$product.name' } }"
    })
    List<ResponseSaleDetailsDto> findDetailsWithProductNameBySaleId(ObjectId saleId);
}
