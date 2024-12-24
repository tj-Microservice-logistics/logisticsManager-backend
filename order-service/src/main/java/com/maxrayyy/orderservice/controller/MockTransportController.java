package com.maxrayyy.orderservice.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.maxrayyy.orderservice.dto.RouteRequestDTO;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/mock")
public class MockTransportController {

    @PostMapping("/route")
    public Map<String, Double> createRoute(@RequestBody RouteRequestDTO request) {
        System.out.println("Mock transport service received request for orderId: " + request.getOrderId());
        System.out.println("From warehouse: " + request.getStartWarehouse() + " to warehouse: " + request.getEndWarehouse());
        
        Map<String, Double> response = new HashMap<>();
        response.put("totalCost", 100.00);
        return response;
    }
} 