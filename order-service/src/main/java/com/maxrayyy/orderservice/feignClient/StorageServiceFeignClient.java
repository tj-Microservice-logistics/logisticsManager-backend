package com.maxrayyy.orderservice.feignClient;

import com.maxrayyy.orderservice.dto.RouteRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "transport-service", url = "localhost:8080/mock")
//@FeignClient(name = "transport-service", url = "localhost:9082")
public interface StorageServiceFeignClient {
    @PostMapping("/route")
    Map<String, Double> createRoute(@RequestBody RouteRequestDTO request);
}
