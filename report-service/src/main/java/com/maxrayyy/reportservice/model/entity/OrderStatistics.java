package com.maxrayyy.reportservice.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "order_statistics")
public class OrderStatistics {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String statPeriod;  // 统计周期 (如"2024-01", "2024")

  private String granularity;  // DAILY, MONTHLY, YEARLY

  private Integer totalOrders = 0;

  private BigDecimal totalAmount = BigDecimal.ZERO;
}
