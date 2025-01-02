// order-service/src/main/java/com/maxrayyy/orderservice/service/OrderService.java

package com.maxrayyy.orderservice.service;

import com.maxrayyy.commonmodule.dto.transportDto.ResponseMessage;
import com.maxrayyy.commonmodule.dto.transportDto.RouteDto;
import com.maxrayyy.orderservice.dto.OrderDTO;
import com.maxrayyy.orderservice.entity.Goods;
import com.maxrayyy.orderservice.entity.Order;
import com.maxrayyy.orderservice.feignClient.TransportServiceClient;
import com.maxrayyy.orderservice.repository.GoodsRepository;
import com.maxrayyy.orderservice.repository.OrderRepository;
import feign.FeignException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import java.util.stream.Collectors;

import com.maxrayyy.orderservice.dto.OrderWithGoodsDTO;

@Service
public class OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;
    private final GoodsRepository goodsRepository;

    @Autowired
    TransportServiceClient transportServiceClient;

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

        // 调用transport-API规划订单路径
        RouteDto routeDto = new RouteDto();
        routeDto.setStartWarehouseName(order.getOriginPlace());
        routeDto.setEndWarehouseName(order.getDestinationPlace());
        routeDto.setOrderNumber(order.getOrderNumber());
        routeDto.setCargoWeight(goods.getGoodsWeight());

        try {
            ResponseMessage<RouteDto> responseMessage = transportServiceClient.addRoute(routeDto);

            order.setPrice(responseMessage.getData().getTotalCost());
        } catch (FeignException e) {
            logger.error("Error creating route in transport service: ", e);
        }

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
    public void updatePriceByOrderId(Long orderId, Integer newPrice) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            order.setPrice(newPrice);
            orderRepository.save(order);
        }
    }

    public List<OrderWithGoodsDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(order -> {
            OrderWithGoodsDTO dto = new OrderWithGoodsDTO();
            BeanUtils.copyProperties(order, dto);
            
            // 获取对应的商品信息
            goodsRepository.findById(order.getGoodsId()).ifPresent(dto::setGoods);

            return dto;
        }).collect(Collectors.toList());
    }

    @Transactional
    public void updateDeliverStatusByOrderId(Long orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            order.setDeliverStatus(order.getDeliverStatus() + 1);
            order.setFinishDate(LocalDate.now());
            orderRepository.save(order);
        }
    }
}