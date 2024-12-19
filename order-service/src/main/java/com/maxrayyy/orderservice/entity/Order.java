// order-service/src/main/java/com/maxrayyy/orderservice/entity/Order.java

package com.maxrayyy.orderservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId; // 使用 orderId 作为主键

    private Long goodsId; // Goods_ID
    private String orderNumber; // 订单编号
    private LocalDate generationDate; // 创建日期
    private LocalDate finishDate; // 完成日期

    private String originPlace; // 起始地址
    private String destinationPlace; // 终点地址
    private Double price; // 价格
    private Boolean paymentCompleted; // 是否完成支付

    private int deliverStatus; // 运输状态
}