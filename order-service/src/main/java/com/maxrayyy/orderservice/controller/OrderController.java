package com.maxrayyy.orderservice.controller;

import com.maxrayyy.orderservice.dto.OrderDTO;
import com.maxrayyy.orderservice.entity.Order;
import com.maxrayyy.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@RefreshScope
public class OrderController {

    @Value("${config.info:default_value}")
    private String configInfo;
    
    private final OrderService orderService;
    
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(value = "/test")
    public String test(){
        return "this is order-service";
    }

    @GetMapping(value = "/test/getConfigInfo")
    public String getConfigInfo(){
        return configInfo;
    }

    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@RequestBody OrderDTO orderDTO) {
        Order order = orderService.createOrder(orderDTO);
        return ResponseEntity.ok(order);
    }
    
    @GetMapping("/{orderNumber}")
    public ResponseEntity<Order> getOrder(@PathVariable String orderNumber) {
        Order order = orderService.getOrder(orderNumber);
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(order);
    }

    @GetMapping("/deliver-status/0")
    public ResponseEntity<List<Order>> getOrdersWithDeliverStatusZero() {
        List<Order> orders = orderService.getAndProcessOrdersWithDeliverStatusZero();
        return ResponseEntity.ok(orders);
    }

    @PostMapping("/update-deliver-status")
    public ResponseEntity<String> updateDeliverStatus(@RequestParam Long goodsId) {
        orderService.updateDeliverStatusByGoodsId(goodsId);
        return ResponseEntity.ok("Deliver status updated for goodsId: " + goodsId);
    }

    @PostMapping("/update-payment-completed")
    public ResponseEntity<String> updatePaymentCompleted(@RequestParam Long orderId) {
        orderService.updatePaymentCompletedByOrderId(orderId);
        return ResponseEntity.ok("Payment completed status updated for orderId: " + orderId);
    }

    @PostMapping("/update-price")
    public ResponseEntity<String> updatePrice(@RequestParam Long orderId, @RequestParam double newPrice) {
        orderService.updatePriceByOrderId(orderId, newPrice);
        return ResponseEntity.ok("Price updated for orderId: " + orderId);
    }
}
