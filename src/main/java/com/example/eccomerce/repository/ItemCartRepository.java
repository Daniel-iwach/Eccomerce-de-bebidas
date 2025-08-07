package com.example.eccomerce.repository;

import com.example.eccomerce.model.ItemCart;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemCartRepository extends MongoRepository<ItemCart,String> {
    Optional<List<ItemCart>>findByCartId(String cartId);
    Optional<ItemCart>findByCartIdAndProductId(String cartId, String productId);

    @Aggregation(pipeline = {
            "{ '$match': { 'cartId': ?0 } }",
            "{ '$group': { '_id': null, 'totalQuantity': { '$sum': '$quantity' } } }"
    })
    Optional<TotalQuantityResult> sumQuantityByCartId(String cartId);

    // Clase proyección para recibir el resultado
    interface TotalQuantityResult {
        Integer getTotalQuantity();
    }
}
