package com.example.eccomerce.repository;

import com.example.eccomerce.model.ItemCart;
import com.example.eccomerce.model.dtos.response.ItemWithProductInfoDto;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemCartRepository extends MongoRepository<ItemCart,String> {
    Optional<List<ItemCart>>findByCartId(ObjectId cartId);

    Long deleteAllByCartId(ObjectId cartId);

    @Aggregation(pipeline = {
            "{ $match: { cartId: ?0 } }",
            "{ $lookup: { from: 'Products', localField: 'productId', foreignField: '_id', as: 'product' } }",
            "{ $unwind: '$product' }",
            "{ $project: { _id: 1, cartId: 1, quantity: 1, subTotal: 1, " +
                    "productName: '$product.name', productPrice: '$product.price', " +
                    "productImage: '$product.urlImagen', productCategory: '$product.category' } }"
    })
    List<ItemWithProductInfoDto> findItemsWithProductInfoByCartId(ObjectId cartId);
    Optional<ItemCart>findByCartIdAndProductId(ObjectId cartId, ObjectId productId);


    @Aggregation(pipeline = {
            "{ '$match': { 'cartId': ?0 } }",
            "{ '$group': { '_id': null, 'totalQuantity': { '$sum': '$quantity' } } }"
    })
    Optional<TotalQuantityResult> sumQuantityByCartId(ObjectId cartId);


    // Clase proyección para recibir el resultado
    interface TotalQuantityResult {
        Integer getTotalQuantity();
    }
}
