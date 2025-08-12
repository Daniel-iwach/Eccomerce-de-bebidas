package com.example.eccomerce.repository;

import com.example.eccomerce.model.SaleDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleDetailsRepository extends MongoRepository<SaleDetails,String> {
}
