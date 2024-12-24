package com.maxrayyy.reportservice.repository;

import com.maxrayyy.reportservice.model.entity.OrderRawData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRawDataRepository extends JpaRepository<OrderRawData, Long> {
    
    // 查询指定时间范围的数据
    @Query("SELECT o FROM OrderRawData o WHERE o.orderCreateTime >= :startDate AND o.orderCreateTime < :endDate " +
           "ORDER BY o.orderCreateTime")
    List<OrderRawData> findByDateRange(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);
}