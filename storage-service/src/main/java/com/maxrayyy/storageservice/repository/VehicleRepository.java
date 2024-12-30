package com.maxrayyy.storageservice.repository;

import com.maxrayyy.storageservice.entity.Vehicle;
import com.maxrayyy.storageservice.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

    Optional<Vehicle> findFirstByWarehouse_WarehouseIdAndStatusTrue(Integer startWarehouseId);
}
