package com.maxrayyy.reportservice.task;

import com.maxrayyy.reportservice.model.entity.OrderRawData;
import com.maxrayyy.reportservice.model.entity.OrderStatistics;
import com.maxrayyy.reportservice.repository.OrderRawDataRepository;
import com.maxrayyy.reportservice.repository.OrderStatisticsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DailyStatisticsTask {
    private final OrderRawDataRepository orderRawDataRepository;
    private final OrderStatisticsRepository orderStatisticsRepository;

    // 每天凌晨1点执行
    @Scheduled(cron = "0 0 1 * * ?")
    @Transactional
    public void calculateDailyStatistics() {
        // 获取昨天的日期
        LocalDate yesterday = LocalDate.now().minusDays(1);
        LocalDateTime startTime = yesterday.atStartOfDay();
        LocalDateTime endTime = yesterday.atTime(LocalTime.MAX);
        
        try {
            log.info("开始统计{}的数据", yesterday);
            
            // 获取昨天的所有订单
            List<OrderRawData> orders = orderRawDataRepository
                    .findByDateRange(startTime, endTime);
            
            if (orders.isEmpty()) {
                log.info("{}没有订单数据", yesterday);
                return;
            }
            
            // 计算总数和总金额
            int totalOrders = orders.size();
            BigDecimal totalAmount = orders.stream()
                    .map(OrderRawData::getAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            
            // 更新或创建统计记录
            OrderStatistics dailyStats = orderStatisticsRepository
                    .findByStatPeriodAndGranularity(
                            yesterday.toString(), "DAILY")
                    .orElse(new OrderStatistics());
            
            dailyStats.setStatPeriod(yesterday.toString());
            dailyStats.setGranularity("DAILY");
            dailyStats.setTotalOrders(totalOrders);
            dailyStats.setTotalAmount(totalAmount);
            
            orderStatisticsRepository.save(dailyStats);
            
            // 更新月度统计
            updateMonthlyStatistics(yesterday);
            
            // 如果是月末，更新年度统计
            if (yesterday.getDayOfMonth() == yesterday.lengthOfMonth()) {
                updateYearlyStatistics(yesterday.getYear());
            }
            
            log.info("{}的统计数据更新完成", yesterday);
        } catch (Exception e) {
            log.error("统计数据更新失败: {}", e.getMessage(), e);
            throw e;
        }
    }
    
    private void updateMonthlyStatistics(LocalDate date) {
        String yearMonth = String.format("%d-%02d", date.getYear(), date.getMonthValue());
        
        // 获取当月所有日统计数据
        List<OrderStatistics> dailyStats = orderStatisticsRepository
                .findByGranularityAndStatPeriodStartingWith("DAILY", yearMonth);
        
        if (!dailyStats.isEmpty()) {
            int totalOrders = dailyStats.stream()
                    .mapToInt(OrderStatistics::getTotalOrders)
                    .sum();
            BigDecimal totalAmount = dailyStats.stream()
                    .map(OrderStatistics::getTotalAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            
            OrderStatistics monthlyStats = orderStatisticsRepository
                    .findByStatPeriodAndGranularity(yearMonth, "MONTHLY")
                    .orElse(new OrderStatistics());
            
            monthlyStats.setStatPeriod(yearMonth);
            monthlyStats.setGranularity("MONTHLY");
            monthlyStats.setTotalOrders(totalOrders);
            monthlyStats.setTotalAmount(totalAmount);
            
            orderStatisticsRepository.save(monthlyStats);
        }
    }
    
    private void updateYearlyStatistics(int year) {
        String yearStr = String.valueOf(year);
        
        // 获取当年所有月统计数据
        List<OrderStatistics> monthlyStats = orderStatisticsRepository
                .findByGranularityAndStatPeriodStartingWith("MONTHLY", yearStr);
        
        if (!monthlyStats.isEmpty()) {
            int totalOrders = monthlyStats.stream()
                    .mapToInt(OrderStatistics::getTotalOrders)
                    .sum();
            BigDecimal totalAmount = monthlyStats.stream()
                    .map(OrderStatistics::getTotalAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            
            OrderStatistics yearlyStats = orderStatisticsRepository
                    .findByStatPeriodAndGranularity(yearStr, "YEARLY")
                    .orElse(new OrderStatistics());
            
            yearlyStats.setStatPeriod(yearStr);
            yearlyStats.setGranularity("YEARLY");
            yearlyStats.setTotalOrders(totalOrders);
            yearlyStats.setTotalAmount(totalAmount);
            
            orderStatisticsRepository.save(yearlyStats);
        }
    }
} 