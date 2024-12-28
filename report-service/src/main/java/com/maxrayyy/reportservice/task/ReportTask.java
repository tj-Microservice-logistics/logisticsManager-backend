package com.maxrayyy.reportservice.task;

import com.maxrayyy.reportservice.service.BatchProcessingService;
import com.maxrayyy.reportservice.service.OrderDataSyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalDate;

@Component
@Slf4j
public class ReportTask {

    @Autowired
    private BatchProcessingService batchProcessingService;
    
    @Autowired
    private OrderDataSyncService orderDataSyncService;

    // 每天凌晨0点30分同步前一天的数据
    @Scheduled(cron = "0 30 0 * * ?")
    public void syncDailyOrderData() {
        LocalDateTime startOfDay = LocalDate.now().minusDays(1).atStartOfDay();
        LocalDateTime endOfDay = LocalDate.now().atStartOfDay();
        
        try {
            orderDataSyncService.syncOrderData(startOfDay, endOfDay);
        } catch (Exception e) {
            log.error("Failed to sync daily order data", e);
        }
    }

    // 每天凌晨1点执行日统计
    @Scheduled(cron = "0 0 1 * * ?")
    public void dailyStatisticsTask() {
        log.info("Starting daily statistics processing...");
        try {
            batchProcessingService.processDailyStatistics();
        } catch (Exception e) {
            log.error("Error in daily statistics processing", e);
        }
    }

    // 每月1号凌晨2点执行月统计
    @Scheduled(cron = "0 0 2 1 * ?")
    public void monthlyStatisticsTask() {
        log.info("Starting monthly statistics processing...");
        try {
            batchProcessingService.processMonthlyStatistics();
        } catch (Exception e) {
            log.error("Error in monthly statistics processing", e);
        }
    }

    // 每年1月1日凌晨3点执行年统计
    @Scheduled(cron = "0 0 3 1 1 ?")
    public void yearlyStatisticsTask() {
        log.info("Starting yearly statistics processing...");
        try {
            batchProcessingService.processYearlyStatistics();
        } catch (Exception e) {
            log.error("Error in yearly statistics processing", e);
        }
    }
} 