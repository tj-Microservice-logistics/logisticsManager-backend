package com.maxrayyy.reportservice.repository;

import com.maxrayyy.reportservice.model.entity.OrderRawData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.math.BigDecimal;

@Repository
public interface OrderRawDataRepository extends JpaRepository<OrderRawData, Long> {
    
    @Query("SELECT o FROM OrderRawData o WHERE o.orderCreateTime >= :startTime AND o.orderCreateTime < :endTime")
    List<OrderRawData> findByDateRange(
        @Param("startTime") LocalDateTime startTime, 
        @Param("endTime") LocalDateTime endTime
    );

    @Query("SELECT COUNT(o) FROM OrderRawData o WHERE o.orderCreateTime >= :startTime AND o.orderCreateTime < :endTime")
    long countByDateRange(
        @Param("startTime") LocalDateTime startTime, 
        @Param("endTime") LocalDateTime endTime
    );
    
    @Query("SELECT SUM(o.amount) FROM OrderRawData o WHERE o.orderCreateTime >= :startTime AND o.orderCreateTime < :endTime")
    BigDecimal sumAmountByDateRange(
        @Param("startTime") LocalDateTime startTime, 
        @Param("endTime") LocalDateTime endTime
    );
}