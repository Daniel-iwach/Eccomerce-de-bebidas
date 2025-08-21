package com.example.eccomerce.repository;

import com.example.eccomerce.model.Sale;
import com.example.eccomerce.model.dtos.response.ResponseSaleSummaryDto;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SaleRepository extends MongoRepository<Sale,String> {
    List<Sale> findByDateTimeBetween(LocalDateTime start, LocalDateTime end);

    @Aggregation(pipeline = {
            "{ $match: { state: 'PENDIENTE', dateTime: { $gte: ?#{[0]}, $lte: ?#{[1]} } } }",
            "{ $group: { _id: null, totalSales: { $sum: 1 }, totalIncome: { $sum: \"$total\" } } }"
    })
    ResponseSaleSummaryDto getSalesSummary(LocalDateTime starTime, LocalDateTime endTime);
}
