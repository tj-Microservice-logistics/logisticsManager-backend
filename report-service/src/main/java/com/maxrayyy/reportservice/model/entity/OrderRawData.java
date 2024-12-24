package com.maxrayyy.reportservice.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "order_raw_data")
public class OrderRawData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long orderId;
    
    @Column(name = "order_status")
    private String orderStatus;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal amount;
    
    @Column(name = "order_create_time")
    private LocalDateTime orderCreateTime;
    
    @Column(name = "order_update_time")
    private LocalDateTime orderUpdateTime;
    
    @Column(name = "create_time", nullable = true)
    private LocalDateTime createTime;
    
    @Column(name = "raw_message", length = 500, nullable = true)
    private String rawMessage;
} 