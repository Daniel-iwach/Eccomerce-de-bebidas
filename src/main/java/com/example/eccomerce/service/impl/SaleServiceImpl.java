package com.example.eccomerce.service.impl;

import com.example.eccomerce.mappers.SaleMapper;
import com.example.eccomerce.model.Sale;

import com.example.eccomerce.model.dtos.request.RequestCreatePayDto;
import com.example.eccomerce.model.dtos.response.*;
import com.example.eccomerce.model.dtos.request.RequestCreateSaleDto;
import com.example.eccomerce.model.dtos.request.RequestFindByDateTime;
import com.example.eccomerce.model.enums.ESaleState;
import com.example.eccomerce.repository.SaleRepository;
import com.example.eccomerce.service.interfaces.ICartService;
import com.example.eccomerce.service.interfaces.IPayService;
import com.example.eccomerce.service.interfaces.ISaleDetailsService;
import com.example.eccomerce.service.interfaces.ISaleService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements ISaleService {
    private final SaleRepository saleRepository;
    private final SaleMapper saleMapper;
    private final ICartService cartService;
    private final ISaleDetailsService saleDetailsService;
    private final IPayService payService;

    @Override
    public ResponseSaleDto createSale(RequestCreateSaleDto createSaleDto) {
        Sale sale= saleMapper.createSaleToSale(createSaleDto);
        sale.setState(ESaleState.PENDIENTE);
        sale.setDateTime(ZonedDateTime.now(ZoneId.of("America/Argentina/Buenos_Aires")).toLocalDateTime());
        sale.setPayId(new ObjectId());
        sale=saleRepository.save(sale);

        ResponsePayDto payDto= payService.createPay(new RequestCreatePayDto(new ObjectId(sale.getId()),sale.getTotal()));
        sale.setPayId(new ObjectId(payDto.id()));

        sale=saleRepository.save(sale);
        System.out.println(sale);

        //OBTIENE CARTID Y CREA LOS SALEDETAILS OBTIENIENDO LA LISTA DE IDS
        String cartId=findCartByUserId(sale.getUserId().toHexString());
        List<String> idsList =saleDetailsService.createSaleDetails(sale.getId(),cartId);

        //SETEA LA LISTA Y GUARDA
        sale.setSaleDetailsList(idsList);
        Sale saleSaved=saleRepository.save(sale);

        return saleMapper.saleToSaleDto(saleSaved);
    }

    @Override
    public List<ResponseSaleDto> listAll() {
        return saleMapper.saleListToSaleDtoList(saleRepository.findAll());
    }



    @Override
    public List<ResponseSaleDto> findByDateTime(RequestFindByDateTime request) {
        if (request.start().isAfter(request.end())) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser despues que la de fin");
        }
        return saleMapper.saleListToSaleDtoList(saleRepository.findByDateTimeBetween(request.start(),request.end()));
    }

    @Override
    public List<ResponseSaleDto> findSalesOfWeek() {
        LocalDate now= LocalDate.now();

        //primer dia de la semana
        LocalDateTime firstDay= now.with(DayOfWeek.MONDAY).atStartOfDay();
        //ultimo dia de la semana
        LocalDateTime lastDay=now.with(DayOfWeek.SUNDAY).atTime(LocalTime.MAX);

        return findByDateTime(new RequestFindByDateTime(firstDay,lastDay));
    }

    @Override
    public List<ResponseSaleDto> findSalesOfMonth() {
        LocalDate now = LocalDate.now();

        // Primer día del mes a las 00:00
        LocalDateTime firstDay = now.withDayOfMonth(1).atStartOfDay();
        // Último día del mes a las 23:59:59
        LocalDateTime lastDay = now.withDayOfMonth(now.lengthOfMonth()).atTime(LocalTime.MAX);

        return findByDateTime(new RequestFindByDateTime(firstDay,lastDay));
    }

    @Override
    public List<ResponseSaleDto> findSalesOfAge() {
        LocalDate now= LocalDate.now();

        //primer dia del año
        LocalDateTime firstDay= LocalDate.of(now.getYear(),Month.JANUARY,1).atStartOfDay();
        //ultimo dia del año
        LocalDateTime lastDay=LocalDate.of(now.getYear(),Month.DECEMBER,31).atTime(LocalTime.MAX);

        return findByDateTime(new RequestFindByDateTime(firstDay,lastDay));
    }



    @Override
    public ResponseSaleSummaryDto getBalanceBetweenDates(RequestFindByDateTime request) {
        if (request.start().isAfter(request.end())) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser despues que la de fin");
        }
        ResponseSaleSummaryDto saleSummary=saleRepository.
                getSalesSummary(request.start(),request.end());
        return saleSummary;
    }

    @Override
    public ResponseSaleSummaryDto getBalanceOfMonth() {
        LocalDate now = LocalDate.now();

        // Primer día del mes a las 00:00
        LocalDateTime firstDay = now.withDayOfMonth(1).atStartOfDay();
        // Último día del mes a las 23:59:59
        LocalDateTime lastDay = now.withDayOfMonth(now.lengthOfMonth()).atTime(LocalTime.MAX);

        return getBalanceBetweenDates(new RequestFindByDateTime(firstDay,lastDay));
    }

    @Override
    public ResponseSaleSummaryDto getBalanceOfWeek() {
        LocalDate now= LocalDate.now();

        //primer dia de la semana
        LocalDateTime firstDay= now.with(DayOfWeek.MONDAY).atStartOfDay();
        //ultimo dia de la semana
        LocalDateTime lastDay=now.with(DayOfWeek.SUNDAY).atTime(LocalTime.MAX);

        return getBalanceBetweenDates(new RequestFindByDateTime(firstDay,lastDay));
    }

    @Override
    public ResponseSaleSummaryDto getBalanceOfAge() {
        LocalDate now= LocalDate.now();

        //primer dia del año
        LocalDateTime firstDay= LocalDate.of(now.getYear(),Month.JANUARY,1).atStartOfDay();
        //ultimo dia del año
        LocalDateTime lastDay=LocalDate.of(now.getYear(),Month.DECEMBER,31).atTime(LocalTime.MAX);

        return getBalanceBetweenDates(new RequestFindByDateTime(firstDay,lastDay));
    }

    @Override
    public ResponseAnnualStatisticsDto getAnnualStatistics(boolean current) {
        ResponseAnnualStatisticsDto annualStatistics= new ResponseAnnualStatisticsDto();
        annualStatistics.setIncomeList(new ArrayList<>());

        LocalDate now = LocalDate.now();
        List<Month> months = Arrays.asList(Month.values());
        int year;
        String income;

        if (current) {
            year = now.getYear(); // año actual
        }else {
            year = now.getYear() - 1; // año anterior
        }
        for (Month month : months) {
            // Usamos YearMonth para manejar meses de distinto largo
            YearMonth yearMonth = YearMonth.of(year, month);

            LocalDateTime firstDay = yearMonth.atDay(1).atStartOfDay();             // primer día del mes
            LocalDateTime lastDay = yearMonth.atEndOfMonth().atTime(LocalTime.MAX);// último día del mes


            ResponseSaleSummaryDto response= getBalanceBetweenDates(new RequestFindByDateTime(firstDay,lastDay));
            if (response==null){
                income="0";
            }else {
                income= response.totalIncome().toString();
            }

            annualStatistics.getIncomeList().add(income);
        }
        return annualStatistics;
    }

    @Override
    public List<SaleReportDTO> findSalesReportOfMonth() {
        LocalDate now = LocalDate.now();

        // Último día = hoy a las 23:59:59
        LocalDateTime lastDay = now.atTime(LocalTime.MAX);

        // Primer día = 30 días antes a las 00:00
        LocalDateTime firstDay = now.minusDays(30).atStartOfDay();
        return saleRepository.findSalesReportBetweenDates(firstDay,lastDay);
    }

    @Override
    public List<SaleReportDTO> findSalesReportOfWeek() {
        LocalDate now = LocalDate.now();

        // Último día = hoy a las 23:59:59
        LocalDateTime lastDay = now.atTime(LocalTime.MAX);

        // Primer día = 30 días antes a las 00:00
        LocalDateTime firstDay = now.minusDays(7).atStartOfDay();
        return saleRepository.findSalesReportBetweenDates(firstDay,lastDay);
    }


    private String findCartByUserId(String userId){
        return cartService.getCartByUserId(userId).id();
    }
}
