package com.maxrayyy.storageservice.repository;

import com.maxrayyy.storageservice.entity.Driver;
import com.maxrayyy.storageservice.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {
    List<Vehicle> findByWarehouseIdAndAssignedToIsNull(Integer warehouseId);
    List<Vehicle> findByAssignedTo(Driver driver); // 添加此方法
}
