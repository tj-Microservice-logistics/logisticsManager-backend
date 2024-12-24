package com.maxrayyy.reportservice.model.message;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderMessage {
    private Long orderId;
    private String orderStatus;
    private BigDecimal amount;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
} 