package com.maxrayyy.reportservice.feign;

import com.maxrayyy.commonmodule.dto.orderDto.ObtainedOrderDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDateTime;
import java.util.List;

@FeignClient(name = "order-service")
public interface OrderServiceClient {
    
    @GetMapping("/orders/range")
    List<ObtainedOrderDto> getOrdersByTimeRange(
        @RequestParam("startTime") LocalDateTime startTime, //2024-
        @RequestParam("endTime") LocalDateTime endTime
    );
} 