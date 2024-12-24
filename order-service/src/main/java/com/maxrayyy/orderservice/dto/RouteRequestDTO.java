package com.maxrayyy.orderservice.dto;

import lombok.Data;

@Data
public class RouteRequestDTO {
    private Long orderId;
    private Integer startWarehouse;
    private Integer endWarehouse;
} 