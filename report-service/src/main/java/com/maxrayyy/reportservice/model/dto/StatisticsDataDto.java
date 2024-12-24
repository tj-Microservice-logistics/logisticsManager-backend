package com.maxrayyy.reportservice.model.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsDataDto {
    private String date;
    private Integer totalOrders;
    private BigDecimal totalAmount;
}