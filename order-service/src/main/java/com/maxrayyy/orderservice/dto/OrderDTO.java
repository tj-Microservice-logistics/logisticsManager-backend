// order-service/src/main/java/com/maxrayyy/orderservice/dto/OrderDTO.java

package com.maxrayyy.orderservice.dto;

import lombok.Data;

@Data
public class OrderDTO {
    private String originPlace; // 起始地址
    private String destinationPlace; // 终点地址
    private Boolean paymentCompleted; // 是否完成支付

    private String goodsName; // 货物名称
    private String goodsType; // 货物类型
    private Double goodsWeight; // 货物重量
    private Double goodsLength; // 货物长度
    private Double goodsWidth; // 货物宽度
    private Double goodsHeight; // 货物高度
}