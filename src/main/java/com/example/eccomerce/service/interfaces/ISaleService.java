package com.example.eccomerce.service.interfaces;


import com.example.eccomerce.model.dtos.response.ResponseAnnualStatisticsDto;
import com.example.eccomerce.model.dtos.response.ResponseSaleSummaryDto;
import com.example.eccomerce.model.dtos.request.RequestCreateSaleDto;
import com.example.eccomerce.model.dtos.request.RequestFindByDateTime;
import com.example.eccomerce.model.dtos.response.ResponseSaleDto;
import com.example.eccomerce.model.dtos.response.SaleReportDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface ISaleService {
    ResponseSaleDto createSale(RequestCreateSaleDto createSaleDto);

    List<ResponseSaleDto>listAll();
    List<ResponseSaleDto>findByDateTime(RequestFindByDateTime requestFindByDateTime);
    List<ResponseSaleDto>findSalesOfWeek();
    List<ResponseSaleDto>findSalesOfMonth();
    List<ResponseSaleDto>findSalesOfAge();


    ResponseSaleSummaryDto getBalanceBetweenDates(RequestFindByDateTime requestFindByDateTime);
    ResponseSaleSummaryDto getBalanceOfMonth();
    ResponseSaleSummaryDto getBalanceOfWeek();
    ResponseSaleSummaryDto getBalanceOfAge();

    ResponseAnnualStatisticsDto getAnnualStatistics(boolean current);

    List<SaleReportDTO> findSalesReportOfMonth();

    public List<SaleReportDTO> findSalesReportOfWeek();

    String changeStateById(String saleId);

}
