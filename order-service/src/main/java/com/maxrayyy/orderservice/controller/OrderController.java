package com.maxrayyy.orderservice.controller;

import com.maxrayyy.orderservice.dto.OrderDTO;
import com.maxrayyy.orderservice.entity.Order;
import com.maxrayyy.orderservice.service.OrderService;
import com.maxrayyy.orderservice.dto.OrderWithGoodsDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
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

    @PostMapping("/update-deliver-status")
    public ResponseEntity<String> updateDeliverStatus(@RequestParam("OrderNumber") String orderNumber) {
        Order order = orderService.getOrder(orderNumber);
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        orderService.updateDeliverStatusByOrderId(order.getOrderId());
        return ResponseEntity.ok("Deliver status updated for order number: " + orderNumber);
    }

    @PostMapping("/update-payment-completed")
    public ResponseEntity<String> updatePaymentCompleted(@RequestParam Long orderId) {
        orderService.updatePaymentCompletedByOrderId(orderId);
        return ResponseEntity.ok("Payment completed status updated for orderNumber: " + orderId);
    }

    @GetMapping("/list")
    public ResponseEntity<List<OrderWithGoodsDTO>> getAllOrders() {
        List<OrderWithGoodsDTO> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }
}
