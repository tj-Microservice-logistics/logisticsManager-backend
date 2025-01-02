package com.maxrayyy.orderservice.dto;

import com.maxrayyy.orderservice.entity.Goods;
import lombok.Data;
import java.time.LocalDate;

@Data
public class OrderWithGoodsDTO {
    private Long orderId;
    private String orderNumber;
    private LocalDate generationDate;
    private LocalDate finishDate;
    private String originPlace;
    private String destinationPlace;
    private Integer price;
    private Boolean paymentCompleted;
    private int deliverStatus;
    private Goods goods;
} 