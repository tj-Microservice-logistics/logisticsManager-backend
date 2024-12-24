package com.maxrayyy.reportservice.model.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class StatisticsSummaryDto {
    private Integer totalOrders;
    private BigDecimal totalAmount;
    private BigDecimal avgDailyOrders;
    private BigDecimal avgDailyAmount;
    private BigDecimal maxDailyOrders;
    private BigDecimal maxDailyAmount;
    private BigDecimal minDailyOrders;
    private BigDecimal minDailyAmount;
} 