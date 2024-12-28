package com.maxrayyy.reportservice.controller;

import com.maxrayyy.reportservice.service.ReportService;
import com.maxrayyy.reportservice.model.dto.StatisticsResponseDto;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.zip.DataFormatException;

@RestController
@RequestMapping("report/statistics")
@RequiredArgsConstructor
@Slf4j
public class ReportController {
    private final ReportService reportService;

    @GetMapping
    public ResponseEntity<StatisticsResponseDto> getStatistics(
            @RequestParam int year,
            @RequestParam(required = false) @Min(1) @Max(12) Integer month) {
        log.info("Getting statistics for year: {}, month: {}", year, month);

        if (month != null) {
            return ResponseEntity.ok(reportService.getMonthlyReport(year, month));
        } else {
            return ResponseEntity.ok(reportService.getYearlyReport(year));
        }
    }

    @GetMapping("/years")
    public ResponseEntity<StatisticsResponseDto> getAllYearsStatistics() {
        return ResponseEntity.ok(reportService.getAllYearsReport());
    }

    @GetMapping("/export")
    public void exportReport(
            @RequestParam int year,
            @RequestParam(required = false) Integer month,
            HttpServletResponse response) throws IOException {
        
        log.info("Exporting report for year: {}, month: {}", year, month);
        
        try {
            // 1. 设置响应头
            String fileName = month != null ? 
                String.format("订单报表_%d年%02d月.xlsx", year, month) :
                String.format("订单报表_%d年.xlsx", year);
            
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + 
                URLEncoder.encode(fileName, "UTF-8"));
            
            // 2. 生成Excel并写入响应流
            XSSFWorkbook workbook = reportService.generateExcelReport(year, month);
            workbook.write(response.getOutputStream());
            workbook.close();
        } catch (Exception e) {
            log.error("Export report failed: {}", e.getMessage());
            // 重置响应
            response.reset();
            response.setContentType("application/json;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            // 使用PrintWriter写入错误信息
            PrintWriter writer = response.getWriter();
            writer.write("{\"error\":\"导出报表失败：" + e.getMessage() + "\"}");
            writer.flush();
        }
    }

}

