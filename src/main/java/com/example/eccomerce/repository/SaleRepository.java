package com.example.eccomerce.repository;

import com.example.eccomerce.model.Sale;
import com.example.eccomerce.model.dtos.response.ResponseSaleSummaryDto;
import com.example.eccomerce.model.dtos.response.SaleReportDTO;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SaleRepository extends MongoRepository<Sale,String> {
    List<Sale> findByDateTimeBetween(LocalDateTime start, LocalDateTime end);

    @Aggregation(pipeline = {
            "{ $match: {dateTime: { $gte: ?#{[0]}, $lte: ?#{[1]} } } }",
            "{ $addFields: { totalAsDecimal: { $toDecimal: \"$total\" } } }",
            "{ $group: { _id: null, totalSales: { $sum: 1 }, totalIncome: { $sum: \"$totalAsDecimal\" } } }"
    })
    ResponseSaleSummaryDto getSalesSummary(LocalDateTime starTime, LocalDateTime endTime);

    @Aggregation(pipeline = {
            "{ $match: { dateTime: { $gte: ?0, $lte: ?1 } } }",
            "{ $lookup: { from: 'users', localField: 'userId', foreignField: '_id', as: 'user' } }",
            "{ $unwind: '$user' }",
            "{ $lookup: { from: 'payments', localField: 'payId', foreignField: '_id', as: 'payment' } }",
            "{ $unwind: '$payment' }",
            "{ $project: { " +
                    "saleId: '$_id', " +
                    "userName: '$user.name', " +
                    "phoneNumber: '$user.numberPhone', " +
                    "paymentState: '$payment.state', " +
                    "saleState: '$state'," +
                    "paymentMethod: '$payment.method', " +
                    "total: '$total', " +
                    "saleDate: '$dateTime' } }",
            "{ $sort: { dateTime: -1 } }"

    })
    List<SaleReportDTO> findSalesReportBetweenDates(LocalDateTime start, LocalDateTime end);
}
