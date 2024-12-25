package com.maxrayyy.storageservice.controller;

import com.maxrayyy.storageservice.entity.Driver;
import com.maxrayyy.storageservice.entity.Vehicle;
import com.maxrayyy.storageservice.entity.Warehouse;
import com.maxrayyy.storageservice.repository.DriverRepository;
import com.maxrayyy.storageservice.repository.VehicleRepository;
import com.maxrayyy.storageservice.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    private final VehicleRepository vehicleRepository;
    private final WarehouseRepository warehouseRepository;
    private final DriverRepository driverRepository;

    @Autowired
    public VehicleController(VehicleRepository vehicleRepository,
                             WarehouseRepository warehouseRepository,
                             DriverRepository driverRepository) {
        this.vehicleRepository = vehicleRepository;
        this.warehouseRepository = warehouseRepository;
        this.driverRepository = driverRepository;
    }

    /**
     * 获取所有车辆及其详细信息（包括仓库和分配司机信息）
     */
    @GetMapping
    public ResponseEntity<?> getAllVehicles() {
        try {
            List<Map<String, Object>> vehiclesWithDetails = vehicleRepository.findAll()
                    .stream()
                    .map(vehicle -> {
                        Map<String, Object> vehicleMap = new HashMap<>();
                        vehicleMap.put("vehicleId", vehicle.getVehicleId());
                        vehicleMap.put("vehicleType", vehicle.getVehicleType());
                        vehicleMap.put("licensePlate", vehicle.getLicensePlate());
                        vehicleMap.put("status", vehicle.getStatus());

                        // 获取仓库名称
                        String warehouseName = Optional.ofNullable(vehicle.getWarehouse())
                                .map(Warehouse::getWarehouseName)
                                .orElse("未知仓库");
                        vehicleMap.put("warehouseName", warehouseName);

                        // 获取分配司机信息
                        String assignedDriverName = Optional.ofNullable(vehicle.getAssignedTo())
                                .map(Driver::getFullName)
                                .orElse("未分配");
                        vehicleMap.put("assignedDriverName", assignedDriverName);

                        return vehicleMap;
                    })
                    .collect(Collectors.toList());

            return ResponseEntity.ok(Map.of(
                    "items", vehiclesWithDetails,
                    "total", vehiclesWithDetails.size()
            ));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "获取车辆列表失败", "message", e.getMessage()));
        }
    }

    /**
     * 根据仓库 ID 获取未分配且空闲的车辆
     */
    @GetMapping("/available")
    public ResponseEntity<?> getAvailableVehicles(@RequestParam Integer warehouseId) {
        if (!warehouseRepository.existsById(warehouseId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("success", false, "message", "仓库不存在"));
        }

        List<Vehicle> vehicles = vehicleRepository.findAvailableVehiclesByWarehouseId(warehouseId);
        return ResponseEntity.ok(vehicles);
    }

    /**
     * 添加新车辆
     */
    @PostMapping
    public ResponseEntity<?> createVehicle(@RequestBody Vehicle vehicle) {
        try {
            if (vehicle.getWarehouseId() != null) {
                Warehouse warehouse = warehouseRepository.findById(vehicle.getWarehouseId())
                        .orElseThrow(() -> new RuntimeException("所属仓库不存在"));
                vehicle.setWarehouse(warehouse);
            }

            if (vehicle.getAssignedTo() != null) {
                Integer driverId = vehicle.getAssignedTo().getDriverId();
                Driver driver = driverRepository.findById(driverId)
                        .orElseThrow(() -> new RuntimeException("分配的司机不存在"));

                if (!driver.getWarehouseId().equals(vehicle.getWarehouseId())) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(Map.of("success", false, "message", "司机和车辆的仓库不匹配"));
                }

                driver.setIsAvailable(false);
                driverRepository.save(driver);
                vehicle.setAssignedTo(driver);
            }

            Vehicle savedVehicle = vehicleRepository.save(vehicle);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("success", true, "data", savedVehicle, "message", "车辆创建成功"));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "error", "添加车辆失败", "message", e.getMessage()));
        }
    }

    /**
     * 更新车辆信息
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateVehicle(@PathVariable Integer id, @RequestBody Vehicle vehicle) {
        try {
            return vehicleRepository.findById(id).map(existingVehicle -> {
                existingVehicle.setVehicleType(vehicle.getVehicleType());
                existingVehicle.setLicensePlate(vehicle.getLicensePlate());
                existingVehicle.setStatus(vehicle.getStatus());

                if (vehicle.getWarehouseId() != null) {
                    Warehouse warehouse = warehouseRepository.findById(vehicle.getWarehouseId())
                            .orElseThrow(() -> new RuntimeException("所属仓库不存在"));
                    existingVehicle.setWarehouse(warehouse);
                }

                Driver existingDriver = existingVehicle.getAssignedTo();
                Driver newDriver = vehicle.getAssignedTo();

                if (existingDriver != null && !existingDriver.equals(newDriver)) {
                    existingDriver.setIsAvailable(true);
                    driverRepository.save(existingDriver);
                    existingVehicle.setAssignedTo(null);
                }

                if (newDriver != null && newDriver.getDriverId() != null) {
                    Integer newDriverId = newDriver.getDriverId();
                    Driver driver = driverRepository.findById(newDriverId)
                            .orElseThrow(() -> new RuntimeException("分配的司机不存在"));

                    if (!driver.getWarehouseId().equals(existingVehicle.getWarehouse().getWarehouseId())) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(Map.of("success", false, "message", "司机和车辆的仓库不匹配"));
                    }

                    driver.setIsAvailable(false);
                    driverRepository.save(driver);
                    existingVehicle.setAssignedTo(driver);
                }

                Vehicle updatedVehicle = vehicleRepository.save(existingVehicle);
                return ResponseEntity.ok(Map.of("success", true, "data", updatedVehicle, "message", "车辆更新成功"));
            }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("success", false, "message", "车辆不存在")));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "error", "更新车辆失败", "message", e.getMessage()));
        }
    }

    /**
     * 删除车辆
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteVehicle(@PathVariable Integer id) {
        try {
            if (vehicleRepository.existsById(id)) {
                Vehicle vehicle = vehicleRepository.findById(id).get();

                if (vehicle.getAssignedTo() != null) {
                    Driver driver = vehicle.getAssignedTo();
                    driver.setIsAvailable(true);
                    driverRepository.save(driver);
                }

                vehicleRepository.deleteById(id);
                return ResponseEntity.ok(Map.of("success", true, "message", "车辆删除成功"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("success", false, "message", "车辆不存在"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "删除车辆失败", "error", e.getMessage()));
        }
    }
}
