// src/main/java/com/maxrayyy/storageservice/repository/DriverRepository.java
package com.maxrayyy.storageservice.repository;

import com.maxrayyy.storageservice.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Integer> {
    // 可以根据需要添加自定义查询方法
}
