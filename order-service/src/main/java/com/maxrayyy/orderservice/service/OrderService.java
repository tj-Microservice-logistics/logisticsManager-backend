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
import java.util.UUID;
import java.util.List;

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
    public List<Order> getAndProcessOrdersWithDeliverStatusZero() {
        List<Order> orders = orderRepository.findByDeliverStatus(0);
        for (Order order : orders) {
            order.setDeliverStatus(1);
            orderRepository.save(order);
        }
        return orders;
    }

    @Transactional
    public void updateDeliverStatusByGoodsId(Long goodsId) {
        List<Order> orders = orderRepository.findByGoodsId(goodsId);
        for (Order order : orders) {
            order.setDeliverStatus(2);
            order.setFinishDate(LocalDate.now());
            orderRepository.save(order);
        }
    }

    @Transactional
    public void updatePaymentCompletedByOrderId(Long orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            order.setPaymentCompleted(true);
            orderRepository.save(order);
        }
    }

    @Transactional
    public void updatePriceByOrderId(Long orderId, double newPrice) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            order.setPrice(newPrice);
            orderRepository.save(order);
        }
    }
}