package com.example.eccomerce.repository;

import com.example.eccomerce.model.Sale;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SaleRepository extends MongoRepository<Sale,String> {
    List<Sale> findByDateTimeBetween(LocalDateTime start, LocalDateTime end);
}
