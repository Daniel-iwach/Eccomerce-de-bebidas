package com.example.eccomerce.repository;

import com.example.eccomerce.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product,String> {
    long countByStock(int stock);
    long countByStockLessThan(int stock);
}
