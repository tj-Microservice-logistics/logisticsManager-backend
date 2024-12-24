package com.maxrayyy.reportservice.service.impl;

import com.maxrayyy.reportservice.model.dto.ObtainedOrderDto;
import com.maxrayyy.reportservice.model.dto.StatisticsDataDto;
import com.maxrayyy.reportservice.model.dto.StatisticsResponseDto;
import com.maxrayyy.reportservice.model.dto.StatisticsSummaryDto;
import com.maxrayyy.reportservice.model.entity.OrderStatistics;
import com.maxrayyy.reportservice.model.entity.OrderRawData;
import com.maxrayyy.reportservice.model.excel.OrderExportData;
import com.maxrayyy.reportservice.model.message.OrderMessage;
import com.maxrayyy.reportservice.repository.OrderStatisticsRepository;
import com.maxrayyy.reportservice.repository.OrderRawDataRepository;
import com.maxrayyy.reportservice.service.ReportService;
import com.maxrayyy.reportservice.util.ExcelUtil;
import com.maxrayyy.reportservice.util.ConvertUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.transaction.annotation.Transactional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportServiceImpl implements ReportService {
    private final OrderStatisticsRepository statisticsRepository;
    private final OrderRawDataRepository orderRawDataRepository;
    
    @Value("${report.file.path}")
    private String baseFilePath;

    @Override
    @Cacheable(value = "monthly_report", key = "#year + '-' + #month")
    public StatisticsResponseDto getMonthlyReport(int year, int month) {
        String yearMonth = String.format("%d-%02d", year, month);
        List<OrderStatistics> statistics = statisticsRepository.getDailyStatsByMonth(yearMonth);
        
        if (statistics.isEmpty()) {
            throw new RuntimeException("未找到数据");
        }
        
        return createResponse(statistics.stream()
                .map(this::convertToStatisticsData)
                .collect(Collectors.toList()));
    }

    @Override
    @Cacheable(value = "yearly_report", key = "#year")
    public StatisticsResponseDto getYearlyReport(int year) {
        List<OrderStatistics> statistics = statisticsRepository.getMonthlyStatsByYear(String.valueOf(year));
        
        if (statistics.isEmpty()) {
            throw new RuntimeException("未找到数据");
        }
        
        return createResponse(statistics.stream()
                .map(this::convertToStatisticsData)
                .collect(Collectors.toList()));
    }

    @Override
    public StatisticsResponseDto getAllYearsReport() {
        List<OrderStatistics> statistics = statisticsRepository.getAllYearlyStats();
        
        if (statistics.isEmpty()) {
            throw new RuntimeException("未找到数据");
        }
        
        return createResponse(statistics.stream()
                .map(this::convertToStatisticsData)
                .collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<byte[]> exportReport(int year, Integer month) {
        LocalDateTime startDate, endDate;
        
        if (month != null) {
            startDate = LocalDateTime.of(year, month, 1, 0, 0);
            endDate = startDate.plusMonths(1);
        } else {
            startDate = LocalDateTime.of(year, 1, 1, 0, 0);
            endDate = startDate.plusYears(1);
        }
        
        List<OrderRawData> rawData = orderRawDataRepository.findByDateRange(startDate, endDate);
        
        if (rawData.isEmpty()) {
            throw new RuntimeException("没有可导出的数据");
        }
        
        List<OrderExportData> exportData = ConvertUtil.toExportDataList(rawData);
        
        String fileName = String.format("report_%d_%s", 
                year,
                month != null ? String.format("%02d", month) : "all");

        return ExcelUtil.generateExcel(exportData, OrderExportData.class, fileName);
    }

    @Override
    @Transactional
    public void processOrderMessage(OrderMessage message) {
        LocalDateTime orderTime = message.getCreateTime();
        
        // 更新月统计
        updateStatistics("MONTHLY", 
                String.format("%d-%02d", orderTime.getYear(), orderTime.getMonthValue()), 
                message);
        
        // 更新年统计
        updateStatistics("YEARLY", 
                String.valueOf(orderTime.getYear()), 
                message);
    }

    @Override
    @Transactional
    public void saveObtainedOrder(ObtainedOrderDto obtainedOrder) {
        OrderRawData orderRawData = new OrderRawData();
        orderRawData.setOrderId(obtainedOrder.getOrderId());
        orderRawData.setOrderStatus(obtainedOrder.getOrderStatus());
        orderRawData.setAmount(obtainedOrder.getAmount());
        orderRawData.setOrderCreateTime(obtainedOrder.getOrderCreateTime());
        orderRawData.setOrderUpdateTime(obtainedOrder.getOrderUpdateTime());
        orderRawDataRepository.save(orderRawData);
    }

    private void updateStatistics(String granularity, String statPeriod, OrderMessage message) {
        OrderStatistics statistics = statisticsRepository
                .findByStatPeriodAndGranularity(statPeriod, granularity)
                .orElse(new OrderStatistics());
        
        statistics.setGranularity(granularity);
        statistics.setStatPeriod(statPeriod);
        statistics.setTotalOrders(statistics.getTotalOrders() + 1);
        statistics.setTotalAmount(statistics.getTotalAmount().add(message.getAmount()));
        
        statisticsRepository.save(statistics);
    }

    private StatisticsResponseDto createResponse(List<StatisticsDataDto> data) {
        StatisticsResponseDto response = new StatisticsResponseDto();
        response.setData(data);
        
        // 计算汇总信息
        StatisticsSummaryDto summary = new StatisticsSummaryDto();
        
        // 计算总计
        summary.setTotalOrders(data.stream()
                .mapToInt(StatisticsDataDto::getTotalOrders)
                .sum());
        summary.setTotalAmount(data.stream()
                .map(StatisticsDataDto::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        
        if (!data.isEmpty()) {
            // 计算平均值
            summary.setAvgDailyOrders(BigDecimal.valueOf(summary.getTotalOrders())
                    .divide(BigDecimal.valueOf(data.size()), 2, RoundingMode.HALF_UP));
            summary.setAvgDailyAmount(summary.getTotalAmount()
                    .divide(BigDecimal.valueOf(data.size()), 2, RoundingMode.HALF_UP));
            
            // 计算最大值
            summary.setMaxDailyOrders(BigDecimal.valueOf(data.stream()
                    .mapToInt(StatisticsDataDto::getTotalOrders)
                    .max()
                    .orElse(0)));
            summary.setMaxDailyAmount(data.stream()
                    .map(StatisticsDataDto::getTotalAmount)
                    .max(BigDecimal::compareTo)
                    .orElse(BigDecimal.ZERO));
            
            // 计算最小值
            summary.setMinDailyOrders(BigDecimal.valueOf(data.stream()
                    .mapToInt(StatisticsDataDto::getTotalOrders)
                    .min()
                    .orElse(0)));
            summary.setMinDailyAmount(data.stream()
                    .map(StatisticsDataDto::getTotalAmount)
                    .min(BigDecimal::compareTo)
                    .orElse(BigDecimal.ZERO));
        }
        
        response.setSummary(summary);
        return response;
    }

    private List<String> getQuarterMonths(int quarter) {
        int startMonth = (quarter - 1) * 3 + 1;
        return List.of(
            String.format("%02d", startMonth),
            String.format("%02d", startMonth + 1),
            String.format("%02d", startMonth + 2)
        );
    }

    private StatisticsDataDto convertToStatisticsData(OrderStatistics statistics) {
        return new StatisticsDataDto(
                statistics.getStatPeriod(),
                statistics.getTotalOrders(),
                statistics.getTotalAmount()
        );
    }
} 