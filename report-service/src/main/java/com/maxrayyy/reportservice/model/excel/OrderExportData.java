package com.maxrayyy.reportservice.model.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderExportData {
    @ExcelProperty("订单ID")
    private Long orderId;

    @ExcelProperty("订单状态")
    private String orderStatus;

    @ExcelProperty("订单金额")
    private BigDecimal amount;

    @ExcelProperty("创建时间")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderCreateTime;

    @ExcelProperty("更新时间")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderUpdateTime;
} 