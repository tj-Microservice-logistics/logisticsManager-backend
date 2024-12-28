package com.maxrayyy.reportservice.model.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ObtainedOrderDto {

    private Long orderId;

    private String orderStatus;

    private BigDecimal amount;

    private LocalDateTime orderCreateTime;

    private LocalDateTime orderUpdateTime;
}
