package com.maxrayyy.orderservice.repository;

import com.maxrayyy.orderservice.entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodsRepository extends JpaRepository<Goods, Long> {
} 