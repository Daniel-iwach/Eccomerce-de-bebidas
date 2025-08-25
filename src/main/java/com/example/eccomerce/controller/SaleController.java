package com.example.eccomerce.controller;

import com.example.eccomerce.model.dtos.response.ResponseAnnualStatisticsDto;
import com.example.eccomerce.model.dtos.response.ResponseSaleSummaryDto;
import com.example.eccomerce.model.dtos.request.RequestCreateSaleDto;
import com.example.eccomerce.model.dtos.request.RequestFindByDateTime;
import com.example.eccomerce.model.dtos.response.ResponseSaleDto;
import com.example.eccomerce.model.dtos.response.SaleReportDTO;
import com.example.eccomerce.service.interfaces.ISaleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/sale")
@RequiredArgsConstructor
public class SaleController {
    private final ISaleService saleService;

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping("/create")
    public ResponseEntity<ResponseSaleDto>createSale(@RequestBody @Valid RequestCreateSaleDto createSaleDto){
        return new ResponseEntity<>(saleService.createSale(createSaleDto), HttpStatus.OK);
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list-all")
    public ResponseEntity<List<ResponseSaleDto>>getAll(){
        return new ResponseEntity<>(saleService.listAll(),HttpStatus.OK);
    }



    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/find-by-date-range")
    public ResponseEntity<List<ResponseSaleDto>>findByDateTime(@RequestBody @Valid RequestFindByDateTime request){
        return new ResponseEntity<>(saleService.findByDateTime(request),HttpStatus.OK);
    }

//    @PreAuthorize("hasRole('ADMIN')")
//    @GetMapping("/sales-of-week")
//    public ResponseEntity<List<ResponseSaleDto>>findSalesOfWeek(){
//        return new ResponseEntity<>(saleService.findSalesOfWeek(),HttpStatus.OK);
//    }
//
//    @PreAuthorize("hasRole('ADMIN')")
//    @GetMapping("/sales-of-month")
//    public ResponseEntity<List<ResponseSaleDto>>findSalesOfMonth(){
//        return new ResponseEntity<>(saleService.findSalesOfMonth(),HttpStatus.OK);
//    }
//
//    @PreAuthorize("hasRole('ADMIN')")
//    @GetMapping("/sales-of-year")
//    public ResponseEntity<List<ResponseSaleDto>>findSalesOfAge(){
//        return new ResponseEntity<>(saleService.findSalesOfAge(),HttpStatus.OK);
//    }





//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get-balance-between-dates")
    public ResponseEntity<ResponseSaleSummaryDto> getBalanceBetweenDates(@RequestBody @Valid RequestFindByDateTime requestFindByDateTime){
        return new ResponseEntity<>(saleService.getBalanceBetweenDates(requestFindByDateTime),HttpStatus.OK);
    }

    @GetMapping("/annual-statics/{current}")
    public ResponseEntity<ResponseAnnualStatisticsDto> getAnnualStatistics(@PathVariable boolean current){
        return new ResponseEntity<>(saleService.getAnnualStatistics(current),HttpStatus.OK);
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get-balance-of-month")
    public ResponseEntity<ResponseSaleSummaryDto> getBalanceOfMonth(){
        return new ResponseEntity<>(saleService.getBalanceOfMonth(),HttpStatus.OK);
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get-balance-of-week")
    public ResponseEntity<ResponseSaleSummaryDto> getBalanceOfWeek(){
        return new ResponseEntity<>(saleService.getBalanceOfWeek(),HttpStatus.OK);
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get-balance-of-year")
    public ResponseEntity<ResponseSaleSummaryDto> getBalanceOfAge(){
        return new ResponseEntity<>(saleService.getBalanceOfAge(),HttpStatus.OK);
    }

    @GetMapping("/get-reports-month")
    public ResponseEntity<List<SaleReportDTO>> findSalesOfMonth(){
        return new ResponseEntity<>(saleService.findSalesReportOfMonth(),HttpStatus.OK);
    }

    @GetMapping("/get-reports-week")
    public ResponseEntity<List<SaleReportDTO>> findSalesOfWeek(){
        return new ResponseEntity<>(saleService.findSalesReportOfWeek(),HttpStatus.OK);
    }


}
