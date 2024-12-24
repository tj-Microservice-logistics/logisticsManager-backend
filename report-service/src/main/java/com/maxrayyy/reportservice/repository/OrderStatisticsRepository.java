package com.maxrayyy.reportservice.repository;

import com.maxrayyy.reportservice.model.entity.OrderStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderStatisticsRepository extends JpaRepository<OrderStatistics, Long> {
    // 查询指定月份的每日数据
    @Query("SELECT o FROM OrderStatistics o WHERE o.granularity = 'DAILY' " +
           "AND o.statPeriod LIKE :yearMonth% " +
           "ORDER BY o.statPeriod")
    List<OrderStatistics> getDailyStatsByMonth(@Param("yearMonth") String yearMonth);
    
    // 查询指定年份的每月数据
    @Query("SELECT o FROM OrderStatistics o WHERE o.granularity = 'MONTHLY' " +
           "AND o.statPeriod LIKE :year% " +
           "ORDER BY o.statPeriod")
    List<OrderStatistics> getMonthlyStatsByYear(@Param("year") String year);
    
    // 查询所有年度数据
    @Query("SELECT o FROM OrderStatistics o WHERE o.granularity = 'YEARLY' " +
           "ORDER BY o.statPeriod")
    List<OrderStatistics> getAllYearlyStats();

    Optional<OrderStatistics> findByStatPeriodAndGranularity(
            @Param("statPeriod") String statPeriod, 
            @Param("granularity") String granularity);
    
    List<OrderStatistics> findByGranularityAndStatPeriodStartingWith(
            @Param("granularity") String granularity, 
            @Param("statPeriod") String statPeriod);
}