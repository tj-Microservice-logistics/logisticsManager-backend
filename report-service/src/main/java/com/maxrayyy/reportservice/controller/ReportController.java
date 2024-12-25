package com.maxrayyy.reportservice.controller;

//import com.maxrayyy.reportservice.model.dto.ObtainedOrderDto;
import com.maxrayyy.commonmodule.dto.orderDto.ObtainedOrderDto;
import com.maxrayyy.reportservice.service.ReportService;
import com.maxrayyy.reportservice.model.dto.StatisticsResponseDto;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<byte[]> exportStatistics(
            @RequestParam int year,
            @RequestParam(required = false) Integer month) {
        log.info("Exporting statistics for year: {}, month: {}", year, month);
        return reportService.exportReport(year, month);
    }

    @PostMapping("/order/raw")
    public ResponseEntity<Void> saveOrderRawData(@RequestBody ObtainedOrderDto obtainedOrderDto) {
        reportService.saveObtainedOrder(obtainedOrderDto);
        return ResponseEntity.ok().build();
    }
}

