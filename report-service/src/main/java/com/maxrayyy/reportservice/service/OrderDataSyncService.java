package com.maxrayyy.reportservice.service;

import java.time.LocalDateTime;

public interface OrderDataSyncService {

    // 同步每日订单数据
    void syncOrderData(LocalDateTime startTime, LocalDateTime endTime);
} 