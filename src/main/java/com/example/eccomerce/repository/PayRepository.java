package com.example.eccomerce.repository;

import com.example.eccomerce.model.Pay;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayRepository extends MongoRepository<Pay,String> {
}
