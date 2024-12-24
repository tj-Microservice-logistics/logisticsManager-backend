// src/main/java/com/maxrayyy/storageservice/controller/VehicleController.java
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    private final VehicleRepository vehicleRepository;
    private final WarehouseRepository warehouseRepository;
    private final DriverRepository driverRepository; // 添加 DriverRepository

    @Autowired
    public VehicleController(VehicleRepository vehicleRepository,
                             WarehouseRepository warehouseRepository,
                             DriverRepository driverRepository) {
        this.vehicleRepository = vehicleRepository;
        this.warehouseRepository = warehouseRepository;
        this.driverRepository = driverRepository; // 初始化 DriverRepository
    }

    // 获取所有车辆及所属仓库信息
    @GetMapping
    public ResponseEntity<?> getAllVehicles() {
        try {
            // 查询所有车辆
            List<Map<String, Object>> vehiclesWithWarehouse = vehicleRepository.findAll()
                    .stream()
                    .map(vehicle -> {
                        Map<String, Object> vehicleMap = new HashMap<>();
                        vehicleMap.put("vehicleId", vehicle.getVehicleId());
                        vehicleMap.put("vehicleType", vehicle.getVehicleType());
                        vehicleMap.put("licensePlate", vehicle.getLicensePlate());
                        vehicleMap.put("status", vehicle.getStatus());

                        // 查询仓库名称，添加 null 检查
                        String warehouseName = "未知仓库";
                        if (vehicle.getWarehouse() != null) {
                            warehouseName = vehicle.getWarehouse().getWarehouseName();
                        }
                        vehicleMap.put("warehouseName", warehouseName);

                        // 获取分配司机信息
                        String assignedDriverName = "未分配";
                        if (vehicle.getAssignedTo() != null) {
                            assignedDriverName = vehicle.getAssignedTo().getFullName();
                        }
                        vehicleMap.put("assignedDriverName", assignedDriverName);

                        return vehicleMap;
                    })
                    .collect(Collectors.toList());

            // 构造返回结果
            Map<String, Object> response = new HashMap<>();
            response.put("items", vehiclesWithWarehouse);
            response.put("total", vehiclesWithWarehouse.size());

            // 打印结果进行调试
            System.out.println("车辆和仓库数据：" + response);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "获取车辆列表失败", "message", e.getMessage()));
        }
    }

    // 查询未分配车辆（根据仓库ID）
    @GetMapping("/available")
    public ResponseEntity<?> getAvailableVehicles(@RequestParam Integer warehouseId) {
        if (!warehouseRepository.existsById(warehouseId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("success", false, "message", "仓库不存在"));
        }

        List<Vehicle> vehicles = vehicleRepository.findByWarehouseIdAndAssignedToIsNull(warehouseId);
        return ResponseEntity.ok(vehicles);
    }

    // 添加新车辆
    @PostMapping
    public ResponseEntity<?> createVehicle(@RequestBody Vehicle vehicle) {
        try {
            // 验证仓库是否存在
            if (vehicle.getWarehouseId() != null && !warehouseRepository.existsById(vehicle.getWarehouseId())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("success", false, "message", "所属仓库不存在"));
            }

            // 如果分配了司机，确保司机存在且与仓库匹配
            if (vehicle.getAssignedTo() != null) {
                // 检查司机是否存在
                Integer driverId = vehicle.getAssignedTo().getDriverId(); // 确保 Driver 实体有 driverId
                if (!driverRepository.existsById(driverId)) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(Map.of("success", false, "message", "分配的司机不存在"));
                }

                Driver driver = driverRepository.findById(driverId).get();

                // 检查司机和车辆的仓库是否匹配
                if (!driver.getWarehouseId().equals(vehicle.getWarehouseId())) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(Map.of("success", false, "message", "司机和车辆的仓库不匹配"));
                }

                // 更新司机状态为忙碌
                driver.setIsAvailable(false);
                driverRepository.save(driver);
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

    // 更新车辆
    @PutMapping("/{id}")
    public ResponseEntity<?> updateVehicle(@PathVariable Integer id, @RequestBody Vehicle vehicle) {
        try {
            return vehicleRepository.findById(id).map(existingVehicle -> {
                existingVehicle.setVehicleType(vehicle.getVehicleType());
                existingVehicle.setLicensePlate(vehicle.getLicensePlate());
                existingVehicle.setStatus(vehicle.getStatus());

                // 更新所属仓库
                if (vehicle.getWarehouseId() != null && !warehouseRepository.existsById(vehicle.getWarehouseId())) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(Map.of("success", false, "message", "所属仓库不存在"));
                }
                existingVehicle.setWarehouseId(vehicle.getWarehouseId());

                // 更新分配司机
                Driver existingDriver = existingVehicle.getAssignedTo();
                Driver newDriver = vehicle.getAssignedTo();

                if (existingDriver != null && (newDriver == null || !existingDriver.equals(newDriver))) {
                    // 取消之前司机的分配
                    existingDriver.setIsAvailable(true);
                    driverRepository.save(existingDriver);
                    existingVehicle.setAssignedTo(null);
                }

                if (newDriver != null) {
                    // 检查新司机是否存在
                    Integer newDriverId = newDriver.getDriverId();
                    if (!driverRepository.existsById(newDriverId)) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(Map.of("success", false, "message", "分配的司机不存在"));
                    }

                    Driver driver = driverRepository.findById(newDriverId).get();

                    // 检查司机和车辆的仓库是否匹配
                    if (!driver.getWarehouseId().equals(existingVehicle.getWarehouseId())) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(Map.of("success", false, "message", "司机和车辆的仓库不匹配"));
                    }

                    // 更新司机状态为忙碌
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

    // 删除车辆
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteVehicle(@PathVariable Integer id) {
        try {
            if (vehicleRepository.existsById(id)) {
                Vehicle vehicle = vehicleRepository.findById(id).get();

                // 如果车辆已分配给司机，更新司机状态为可用
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
