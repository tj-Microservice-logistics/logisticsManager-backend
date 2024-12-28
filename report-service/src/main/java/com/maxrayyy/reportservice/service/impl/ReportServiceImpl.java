package com.maxrayyy.reportservice.service.impl;

import com.maxrayyy.reportservice.model.dto.StatisticsDataDto;
import com.maxrayyy.reportservice.model.dto.StatisticsResponseDto;
import com.maxrayyy.reportservice.model.dto.StatisticsSummaryDto;
import com.maxrayyy.reportservice.model.entity.OrderStatistics;
import com.maxrayyy.reportservice.model.entity.OrderRawData;
import com.maxrayyy.reportservice.model.excel.OrderExportData;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.List;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
        try {
            // 1. 获取数据
            List<OrderStatistics> statistics;
            String period;
            String fileName;
            
            if (month != null) {
                // 导出月度报表
                period = String.format("%d-%02d", year, month);
                statistics = statisticsRepository.getDailyStatsByMonth(period);
                fileName = String.format("月度订单报表_%d年%02d月.xlsx", year, month);
            } else {
                // 导出年度报表
                period = String.valueOf(year);
                statistics = statisticsRepository.getDailyStatsByYear(period);
                fileName = String.format("年度订单报表_%d年.xlsx", year);
            }
            
            // 2. 生成Excel
            XSSFWorkbook workbook = generateExcelReport(statistics, year, month);
            
            // 3. 转换为字节数组
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            byte[] excelBytes = outputStream.toByteArray();
            
            // 4. 设置响应头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", URLEncoder.encode(fileName, "UTF-8"));
            
            return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);
            
        } catch (Exception e) {
            log.error("Export report failed", e);
            throw new RuntimeException("导出报表失败", e);
        }
    }
    
    @Override
    public XSSFWorkbook generateExcelReport(int year, Integer month) throws IOException {
        // 1. 获取数据
        List<OrderStatistics> statistics;
        String period;
        
        if (month != null) {
            period = String.format("%d-%02d", year, month);
            statistics = statisticsRepository.getDailyStatsByMonth(period);
        } else {
            period = String.valueOf(year);
            statistics = statisticsRepository.getDailyStatsByYear(period);
        }
        
        // 2. 生成Excel (使用之前的 generateExcelReport 方法)
        return generateExcelReport(statistics, year, month);
    }

    private XSSFWorkbook generateExcelReport(List<OrderStatistics> statistics, int year, Integer month) throws IOException {
        // 1. 读取模板
        InputStream templateStream = getClass().getClassLoader().getResourceAsStream("templates/订单数据报模板.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(templateStream);
        XSSFSheet sheet = workbook.getSheet("Sheet1");
        
        // 2. 填充报表标题 (BCDE合并单元格)
        String reportTitle = month != null ? 
            String.format("%d年%02d月订单报表", year, month) :
            String.format("%d年订单报表", year);
        // 获取合并的单元格并设置值
        sheet.getRow(0).getCell(1).setCellValue(reportTitle);  // B1单元格
        
        // 3. 计算汇总数据
        int totalOrders = statistics.stream().mapToInt(OrderStatistics::getTotalOrders).sum();
        BigDecimal totalAmount = statistics.stream()
            .map(OrderStatistics::getTotalAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        // 计算日均数据
        double avgOrders = statistics.isEmpty() ? 0 : (double) totalOrders / statistics.size();
        BigDecimal avgAmount = statistics.isEmpty() ? BigDecimal.ZERO : 
            totalAmount.divide(BigDecimal.valueOf(statistics.size()), 2, RoundingMode.HALF_UP);
        
        // 4. 填充数据概览
        sheet.getRow(3).getCell(2).setCellValue(totalOrders);      // C4单元格：总订单数
        sheet.getRow(3).getCell(4).setCellValue(totalAmount.doubleValue());  // E4单元格：总金额
        sheet.getRow(4).getCell(2).setCellValue(avgOrders);        // C5单元格：日均订单数
        sheet.getRow(4).getCell(4).setCellValue(avgAmount.doubleValue());    // E5单元格：日均金额
        
        // 5. 填充数据明细
        int rowNum = 7;  // 从第8行开始填充明细数据
        for (OrderStatistics stat : statistics) {
            XSSFRow row = sheet.getRow(rowNum++);
            row.getCell(1).setCellValue(stat.getStatPeriod());     // B列：日期
            row.getCell(2).setCellValue(stat.getTotalOrders());    // C列：订单数
            row.getCell(3).setCellValue(stat.getTotalAmount().doubleValue());  // D列：金额
            
            // 计算并填充每单平均金额
            double avgPerOrder = stat.getTotalOrders() == 0 ? 0 : 
                stat.getTotalAmount().divide(BigDecimal.valueOf(stat.getTotalOrders()), 2, RoundingMode.HALF_UP).doubleValue();
            row.getCell(4).setCellValue(avgPerOrder);              // E列：每单平均金额
        }
        
        return workbook;
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

    private StatisticsDataDto convertToStatisticsData(OrderStatistics statistics) {
        return new StatisticsDataDto(
                statistics.getStatPeriod(),
                statistics.getTotalOrders(),
                statistics.getTotalAmount()
        );
    }
} 