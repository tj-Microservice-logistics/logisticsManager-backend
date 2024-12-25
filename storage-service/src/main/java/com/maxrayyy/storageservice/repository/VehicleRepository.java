package com.maxrayyy.storageservice.repository;

import com.maxrayyy.storageservice.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

    @Query("SELECT v FROM Vehicle v WHERE v.warehouse.warehouseId = :warehouseId AND v.assignedTo IS NULL AND v.status = true")
    List<Vehicle> findAvailableVehiclesByWarehouseId(@Param("warehouseId") Integer warehouseId);

    @Query("SELECT v FROM Vehicle v JOIN FETCH v.warehouse WHERE v.assignedTo IS NULL AND v.status = true")
    List<Vehicle> findAllAvailableVehiclesWithWarehouse();

    @Query("SELECT v FROM Vehicle v WHERE v.assignedTo.driverId = :driverId")
    List<Vehicle> findByAssignedToDriverId(@Param("driverId") Integer driverId);
}
