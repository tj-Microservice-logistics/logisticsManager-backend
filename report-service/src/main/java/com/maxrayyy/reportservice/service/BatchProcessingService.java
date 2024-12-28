package com.maxrayyy.reportservice.service;

public interface BatchProcessingService {

    // 批处理统计日订单
    void processDailyStatistics();

    // 批处理统计月订单
    void processMonthlyStatistics();

    // 批处理统计年订单
    void processYearlyStatistics();
} 