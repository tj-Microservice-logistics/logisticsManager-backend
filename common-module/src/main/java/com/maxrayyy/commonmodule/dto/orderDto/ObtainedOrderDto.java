package com.maxrayyy.commonmodule.dto.orderDto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

//订单创建后传给report-service的order对象
// path: report/statistics/order/raw
@Data
public class ObtainedOrderDto {
    private Long orderId;

    private String orderStatus;

    private BigDecimal amount;

    private LocalDateTime orderCreateTime;

    private LocalDateTime orderUpdateTime;
}

