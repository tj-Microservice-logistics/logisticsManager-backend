package com.maxrayyy.storageservice.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "order-service", url = "http://localhost:9080")
public interface OrderServiceClient {
    @PostMapping("/order/update-deliver-status")
    ResponseEntity<String> updateDeliverStatus(@RequestParam("OrderNumber") String orderNumber);

}