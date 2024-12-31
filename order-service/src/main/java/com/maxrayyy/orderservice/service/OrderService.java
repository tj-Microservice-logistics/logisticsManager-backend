// order-service/src/main/java/com/maxrayyy/orderservice/service/OrderService.java

package com.maxrayyy.orderservice.service;

import com.maxrayyy.orderservice.dto.OrderDTO;
import com.maxrayyy.orderservice.entity.Goods;
import com.maxrayyy.orderservice.entity.Order;
import com.maxrayyy.orderservice.repository.GoodsRepository;
import com.maxrayyy.orderservice.repository.OrderRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.List;
import java.util.stream.Collectors;

import com.maxrayyy.orderservice.dto.OrderWithGoodsDTO;

@Service
public class OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;
    private final GoodsRepository goodsRepository;

    public OrderService(OrderRepository orderRepository, GoodsRepository goodsRepository) {
        this.orderRepository = orderRepository;
        this.goodsRepository = goodsRepository;
    }

    @Transactional
    public Order createOrder(OrderDTO orderDTO) {
        Goods goods = new Goods();
        BeanUtils.copyProperties(orderDTO, goods);
        goods = goodsRepository.save(goods);
        logger.info("Goods saved with ID: {}", goods.getGoodsId());

        Order order = new Order();
        BeanUtils.copyProperties(orderDTO, order);
        order.setGoodsId(goods.getGoodsId());
        order.setOrderNumber(generateOrderNumber());
        order.setDeliverStatus(0);
        order.setGenerationDate(LocalDate.now());
        order.setFinishDate(null);
        order.setPrice(null);

        return orderRepository.save(order);
    }

    public Order getOrder(String orderNumber) {
        logger.info("Fetching order with order number: {}", orderNumber);
        return orderRepository.findByOrderNumber(orderNumber);
    }

    private String generateOrderNumber() {
        return "ORDER-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    @Transactional
    public void updatePaymentCompletedByOrderId(Long orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            order.setPaymentCompleted(true);
            orderRepository.save(order);
        }
    }

    public List<OrderWithGoodsDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(order -> {
            OrderWithGoodsDTO dto = new OrderWithGoodsDTO();
            BeanUtils.copyProperties(order, dto);
            
            // 获取对应的商品信息
            Goods goods = goodsRepository.findById(order.getGoodsId())
                    .orElse(null);
            if (goods != null) {
                dto.setGoods(goods);
            }
            
            return dto;
        }).collect(Collectors.toList());
    }

    @Transactional
    public void updateDeliverStatusByOrderId(Long orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            order.setDeliverStatus(order.getDeliverStatus() + 1);
            orderRepository.save(order);
        }
    }

    public List<OrderWithGoodsDTO> getOrdersByTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        // 转换 LocalDateTime 为 LocalDate
        LocalDate startDate = startTime.toLocalDate();
        LocalDate endDate = endTime.toLocalDate();
        
        List<Order> orders = orderRepository.findByGenerationDateBetween(startDate, endDate);
        
        return orders.stream().map(order -> {
            OrderWithGoodsDTO dto = new OrderWithGoodsDTO();
            BeanUtils.copyProperties(order, dto);
            
            // 获取对应的商品信息
            Goods goods = goodsRepository.findById(order.getGoodsId())
                    .orElse(null);
            if (goods != null) {
                dto.setGoods(goods);
            }
            
            return dto;
        }).collect(Collectors.toList());
    }
}