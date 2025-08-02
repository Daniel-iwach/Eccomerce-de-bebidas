package com.example.eccomerce.repository;

import com.example.eccomerce.model.ItemCart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemCartRepository extends MongoRepository<ItemCart,String> {
}
