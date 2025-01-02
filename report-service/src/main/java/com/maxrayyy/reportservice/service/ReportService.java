package com.maxrayyy.reportservice.service;

//import com.maxrayyy.reportservice.model.dto.ObtainedOrderDto;
import com.maxrayyy.commonmodule.dto.orderDto.ObtainedOrderDto;
import com.maxrayyy.reportservice.model.dto.StatisticsResponseDto;
import org.springframework.http.ResponseEntity;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.IOException;

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
     * 生成Excel报表
     */
    XSSFWorkbook generateExcelReport(int year, Integer month) throws IOException;
} 