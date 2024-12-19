// order-service/src/main/java/com/maxrayyy/orderservice/repository/OrderRepository.java

package com.maxrayyy.orderservice.repository;

import com.maxrayyy.orderservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findByOrderNumber(String orderNumber);
    List<Order> findByDeliverStatus(int deliverStatus);
    List<Order> findByGoodsId(Long goodsId);
}