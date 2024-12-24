package com.maxrayyy.storageservice.repository;

import com.maxrayyy.storageservice.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Integer> {
    // Custom method to find warehouses by their name (optional)
    List<Warehouse> findByWarehouseNameContaining(String warehouseName);
}
