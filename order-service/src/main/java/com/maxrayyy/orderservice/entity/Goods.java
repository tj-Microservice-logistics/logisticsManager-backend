package com.maxrayyy.orderservice.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "goods")
public class Goods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long goodsId; // Goods_ID

    private String goodsName; // 货物名称
    private String goodsType; // 货物类型
    private Double goodsWeight; // 货物重量
    private Double goodsLength; // 货物长度
    private Double goodsWidth; // 货物宽度
    private Double goodsHeight; // 货物高度
} 