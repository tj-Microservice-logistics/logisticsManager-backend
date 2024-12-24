package com.maxrayyy.reportservice.service;

import com.maxrayyy.reportservice.model.dto.ObtainedOrderDto;
import com.maxrayyy.reportservice.model.dto.StatisticsResponseDto;
import com.maxrayyy.reportservice.model.message.OrderMessage;
import org.springframework.http.ResponseEntity;

public interface ReportService {

    /**
     * 获取月度报表
     */
    StatisticsResponseDto getMonthlyReport(int year, int month);
    
    /**
     * 获取年度报表
     */
    StatisticsResponseDto getYearlyReport(int year);

    /**
     * 获取所有年份的报表
     */
    StatisticsResponseDto getAllYearsReport();
    
    /**
     * 导出报表
     */
    ResponseEntity<byte[]> exportReport(int year, Integer month);
    
    /**
     * 处理订单消息
     */
    void processOrderMessage(OrderMessage message);

    /**
     * 保存订单原始数据
     */
    void saveObtainedOrder(ObtainedOrderDto obtainedOrder);
} 