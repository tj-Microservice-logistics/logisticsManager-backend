package com.maxrayyy.storageservice.repository;

import com.maxrayyy.storageservice.entity.Driver;
import com.maxrayyy.storageservice.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Integer> {

    Optional<Driver> findFirstByWarehouseIdAndIsAvailableTrue(Integer startWarehouseId);

    Optional<Driver> findByFullName(String driverName);
}
