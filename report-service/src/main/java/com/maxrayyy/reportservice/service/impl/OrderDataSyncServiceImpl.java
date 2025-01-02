package com.maxrayyy.reportservice.service.impl;

import com.maxrayyy.commonmodule.dto.orderDto.ObtainedOrderDto;
import com.maxrayyy.reportservice.feign.OrderServiceClient;
import com.maxrayyy.reportservice.model.entity.OrderRawData;
import com.maxrayyy.reportservice.repository.OrderRawDataRepository;
import com.maxrayyy.reportservice.service.OrderDataSyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class OrderDataSyncServiceImpl implements OrderDataSyncService {
    
    @Autowired
    private OrderServiceClient orderServiceClient;
    
    @Autowired
    private OrderRawDataRepository orderRawDataRepository;

    @Override
    @Transactional
    public void syncOrderData(LocalDateTime startTime, LocalDateTime endTime) {
        try {
            log.info("Starting order data sync for period: {} to {}", startTime, endTime);
            List<ObtainedOrderDto> orders = orderServiceClient.getOrdersByTimeRange(startTime, endTime);
            
            for (ObtainedOrderDto orderDto : orders) {
                OrderRawData rawData = convertToRawData(orderDto);
                orderRawDataRepository.save(rawData);
            }
            
            log.info("Order data sync completed, processed {} orders", orders.size());
        } catch (Exception e) {
            log.error("Error syncing order data", e);
            throw e;
        }
    }

    private OrderRawData convertToRawData(ObtainedOrderDto orderDto) {
        OrderRawData rawData = new OrderRawData();
        rawData.setOrderId(orderDto.getOrderId());
        rawData.setOrderStatus(orderDto.getOrderStatus());
        rawData.setAmount(orderDto.getAmount());
        rawData.setOrderCreateTime(orderDto.getOrderCreateTime());
        rawData.setOrderUpdateTime(orderDto.getOrderUpdateTime());
        rawData.setCreateTime(LocalDateTime.now());
        return rawData;
    }
}
