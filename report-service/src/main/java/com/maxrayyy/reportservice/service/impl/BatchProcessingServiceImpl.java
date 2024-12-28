package com.maxrayyy.reportservice.service.impl;

import com.maxrayyy.reportservice.model.entity.OrderStatistics;
import com.maxrayyy.reportservice.repository.OrderRawDataRepository;
import com.maxrayyy.reportservice.repository.OrderStatisticsRepository;
import com.maxrayyy.reportservice.service.BatchProcessingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
public class BatchProcessingServiceImpl implements BatchProcessingService {

    @Autowired
    private OrderRawDataRepository orderRawDataRepository;
    
    @Autowired
    private OrderStatisticsRepository orderStatisticsRepository;

    @Override
    @Transactional
    public void processDailyStatistics() {
        LocalDateTime startOfDay = LocalDate.now().minusDays(1).atStartOfDay(); // 昨天00:00:00
        LocalDateTime endOfDay = LocalDate.now().atStartOfDay(); // 今天00:00:00
        
        try {
            long totalOrders = orderRawDataRepository.countByDateRange(startOfDay, endOfDay);
            BigDecimal totalAmount = orderRawDataRepository.sumAmountByDateRange(startOfDay, endOfDay);
            
            String statPeriod = startOfDay.format(DateTimeFormatter.ISO_DATE); // yyyy-MM-dd
            saveStatistics("DAILY", statPeriod, totalOrders, totalAmount);
            
            log.info("Daily statistics processed for {}: {} orders, total amount: {}", 
                statPeriod, totalOrders, totalAmount);
        } catch (Exception e) {
            log.error("Error processing daily statistics", e);
            throw e;
        }
    }

    @Override
    @Transactional
    public void processMonthlyStatistics() {
        LocalDate startOfLastMonth = LocalDate.now().minusMonths(1).withDayOfMonth(1);
        LocalDate endOfLastMonth = LocalDate.now().withDayOfMonth(1);

        try {
            List<OrderStatistics> dailyStats = orderStatisticsRepository
                .findByGranularityAndStatPeriodBetween(
                    "DAILY",
                    startOfLastMonth.format(DateTimeFormatter.ISO_DATE),
                    endOfLastMonth.format(DateTimeFormatter.ISO_DATE)
                );

            int totalOrders = dailyStats.stream()
                .mapToInt(OrderStatistics::getTotalOrders)
                .sum();
            BigDecimal totalAmount = dailyStats.stream()
                .map(OrderStatistics::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

            String monthPeriod = startOfLastMonth.format(DateTimeFormatter.ofPattern("yyyy-MM"));
            saveStatistics("MONTHLY", monthPeriod, totalOrders, totalAmount);

            log.info("Monthly statistics processed for {}: {} orders, total amount: {}",
                monthPeriod, totalOrders, totalAmount);
        } catch (Exception e) {
            log.error("Error processing monthly statistics", e);
            throw e;
        }
    }

    @Override
    @Transactional
    public void processYearlyStatistics() {
        int lastYear = LocalDate.now().getYear() - 1;
        
        try {
            List<OrderStatistics> monthlyStats = orderStatisticsRepository
                .findByGranularityAndStatPeriodStartsWith(
                    "MONTHLY",
                    String.valueOf(lastYear)
                );
            
            int totalOrders = monthlyStats.stream()
                .mapToInt(OrderStatistics::getTotalOrders)
                .sum();
            BigDecimal totalAmount = monthlyStats.stream()
                .map(OrderStatistics::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
            
            saveStatistics("YEARLY", String.valueOf(lastYear), totalOrders, totalAmount);
            
            log.info("Yearly statistics processed for {}: {} orders, total amount: {}", 
                lastYear, totalOrders, totalAmount);
        } catch (Exception e) {
            log.error("Error processing yearly statistics", e);
            throw e;
        }
    }

    private void saveStatistics(String granularity, String period, long totalOrders, BigDecimal totalAmount) {
        OrderStatistics statistics = new OrderStatistics();
        statistics.setGranularity(granularity);
        statistics.setStatPeriod(period);
        statistics.setTotalOrders((int) totalOrders);
        statistics.setTotalAmount(totalAmount != null ? totalAmount : BigDecimal.ZERO);
        orderStatisticsRepository.save(statistics);
    }
}
