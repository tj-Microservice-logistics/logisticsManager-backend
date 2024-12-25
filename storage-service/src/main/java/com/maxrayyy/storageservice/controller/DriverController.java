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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/drivers")
public class DriverController {

    private final DriverRepository driverRepository;
    private final VehicleRepository vehicleRepository;

    @Autowired
    public DriverController(DriverRepository driverRepository, VehicleRepository vehicleRepository) {
        this.driverRepository = driverRepository;
        this.vehicleRepository = vehicleRepository;
    }

    @Autowired
    private WarehouseRepository warehouseRepository;

    /**
     * 根据司机所属仓库查询未分配且空闲的车辆
     * @param driverId 司机 ID
     * @return 车辆列表
     */
    @GetMapping("/{driverId}/available-vehicles")
    public ResponseEntity<List<Vehicle>> getAvailableVehiclesForDriver(@PathVariable Integer driverId) {
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new RuntimeException("司机不存在"));

        Integer warehouseId = driver.getWarehouse().getWarehouseId();
        List<Vehicle> vehicles = vehicleRepository.findAvailableVehiclesByWarehouseId(warehouseId);

        return ResponseEntity.ok(vehicles);
    }


    @GetMapping("/unassigned")
    public ResponseEntity<List<Driver>> getUnassignedDrivers() {
        List<Driver> drivers = driverRepository.findAllUnassignedDriversWithWarehouse();
        return ResponseEntity.ok(drivers);
    }

    // 获取所有司机
    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAllDrivers() {
        List<Map<String, Object>> driversWithWarehouse = driverRepository.findAll()
                .stream()
                .map(driver -> {
                    Map<String, Object> driverMap = new HashMap<>();
                    driverMap.put("driverId", driver.getDriverId());
                    driverMap.put("fullName", driver.getFullName());
                    driverMap.put("contactNumber", driver.getContactNumber());
                    driverMap.put("available", driver.getIsAvailable());
                    driverMap.put("warehouseId", driver.getWarehouseId());

                    // 获取分配车辆信息（假设每个司机只能分配一辆车）
                    Vehicle assignedVehicle = vehicleRepository.findByAssignedToDriverId(driver.getDriverId())
                            .stream().findFirst().orElse(null);
                    driverMap.put("assignedVehicleLicensePlate",
                            assignedVehicle != null ? assignedVehicle.getLicensePlate() : null);

                    // 查询仓库名称
                    String warehouseName = warehouseRepository.findById(driver.getWarehouseId())
                            .map(Warehouse::getWarehouseName)
                            .orElse("未知仓库");
                    driverMap.put("warehouseName", warehouseName);

                    return driverMap;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(driversWithWarehouse);
    }


    // 根据ID获取单个司机
    @GetMapping("/{id}")
    public ResponseEntity<?> getDriverById(@PathVariable Integer id) {
        return driverRepository.findById(id).map(existingDriver -> {
            // 构造 DriverDto
            DriverDto dto = new DriverDto();
            dto.setDriverId(existingDriver.getDriverId());
            dto.setFullName(existingDriver.getFullName());
            dto.setContactNumber(existingDriver.getContactNumber());
            dto.setAvailable(existingDriver.getIsAvailable());

            // 判断是否有分配车辆
            if (existingDriver.getAssignedVehicle() != null) {
                dto.setAssignedVehicleId(existingDriver.getAssignedVehicle().getVehicleId());
                dto.setAssignedVehicleLicensePlate(existingDriver.getAssignedVehicle().getLicensePlate());
            }

            // 返回构造的 DTO
            return ResponseEntity.ok(dto);

}).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DriverDto()));    }

    // 添加新司机
    @PostMapping
    public ResponseEntity<Driver> createDriver(@RequestBody Driver driver) {
        // 使用 warehouseId 查找 Warehouse
        if (driver.getWarehouseId() != null) {
            Warehouse warehouse = warehouseRepository.findById(driver.getWarehouseId())
                    .orElseThrow(() -> new RuntimeException("仓库不存在"));
            driver.setWarehouse(warehouse); // 设置关联的 Warehouse
        }

        driver.setIsAvailable(true); // 默认设置为可用
        return ResponseEntity.ok(driverRepository.save(driver));
    }


    // 更新司机信息
    @PutMapping("/{id}")
    public ResponseEntity<?> updateDriver(@PathVariable Integer id, @RequestBody DriverDto driverDto) {
        return driverRepository.findById(id).map(existingDriver -> {
            // 更新司机基本信息
            existingDriver.setFullName(driverDto.getFullName());
            existingDriver.setContactNumber(driverDto.getContactNumber());
            existingDriver.setWarehouseId(driverDto.getWarehouseId());
            existingDriver.setIsAvailable(driverDto.getAvailable());

            // 更新车辆分配逻辑
            if (driverDto.getAssignedVehicleId() != null) {
                // 查找新分配的车辆
                Vehicle newVehicle = vehicleRepository.findById(driverDto.getAssignedVehicleId())
                        .orElseThrow(() -> new RuntimeException("车辆不存在"));

                // 如果司机之前已分配车辆，释放旧车辆
                if (existingDriver.getAssignedVehicle() != null) {
                    Vehicle oldVehicle = existingDriver.getAssignedVehicle();
                    oldVehicle.setAssignedTo(null);
                    oldVehicle.setStatus(true); // 标记旧车辆为可用
                    vehicleRepository.save(oldVehicle);
                }

                // 绑定新车辆到司机
                existingDriver.setAssignedVehicle(newVehicle);
                newVehicle.setAssignedTo(existingDriver);
                newVehicle.setStatus(false); // 标记新车辆为已分配
                vehicleRepository.save(newVehicle);

                // 设置司机为忙碌状态
                existingDriver.setIsAvailable(false);
            } else {
                // 如果清空车辆分配，释放旧车辆
                if (existingDriver.getAssignedVehicle() != null) {
                    Vehicle oldVehicle = existingDriver.getAssignedVehicle();
                    oldVehicle.setAssignedTo(null);
                    oldVehicle.setStatus(true); // 标记旧车辆为可用
                    vehicleRepository.save(oldVehicle);
                }
                existingDriver.setAssignedVehicle(null);
            }

            // 保存司机更新
            driverRepository.save(existingDriver);

            // 返回更新后的数据
            DriverDto responseDto = createDriverDto(existingDriver);
            return ResponseEntity.ok(responseDto);
        }).orElse(ResponseEntity.notFound().build());
    }

    // 工具方法：创建 DriverDto
    private DriverDto createDriverDto(Driver driver) {
        DriverDto dto = new DriverDto();
        dto.setDriverId(driver.getDriverId());
        dto.setFullName(driver.getFullName());
        dto.setContactNumber(driver.getContactNumber());
        dto.setWarehouseId(driver.getWarehouseId());
        dto.setAvailable(driver.getIsAvailable());

        if (driver.getAssignedVehicle() != null) {
            dto.setAssignedVehicleId(driver.getAssignedVehicle().getVehicleId());
            dto.setAssignedVehicleLicensePlate(driver.getAssignedVehicle().getLicensePlate());
        }

        return dto;
    }

    // 分配车辆给司机
    @PostMapping("/{driverId}/assignVehicle/{vehicleId}")
    public ResponseEntity<?> assignVehicle(@PathVariable Integer driverId, @PathVariable Integer vehicleId) {
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new RuntimeException("司机不存在"));
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("车辆不存在"));

        // 检查司机和车辆的仓库是否匹配
        if (!driver.getWarehouseId().equals(vehicle.getWarehouseId())) {
            return ResponseEntity.badRequest().body("司机和车辆的仓库不匹配");
        }

        // 检查车辆是否已经被分配
        if (vehicle.getAssignedTo() != null) {
            return ResponseEntity.badRequest().body("车辆已被其他司机使用");
        }

        // 分配车辆给司机
        vehicle.setAssignedTo(driver); // 传递 Driver 对象
        vehicle.setStatus(true);
        driver.setIsAvailable(false);

        vehicleRepository.save(vehicle);
        driverRepository.save(driver);

        return ResponseEntity.ok("分配成功");
    }


    // 删除司机
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDriver(@PathVariable Integer id) {
        if (driverRepository.existsById(id)) {
            Driver driver = driverRepository.findById(id).get();

            // 在删除司机前，确保其分配的车辆被处理（例如，取消分配）
            List<Vehicle> assignedVehicles = vehicleRepository.findByAssignedToDriverId(driver.getDriverId());
            for (Vehicle vehicle : assignedVehicles) {
                vehicle.setAssignedTo(null);
                vehicle.setStatus(true);
                vehicleRepository.save(vehicle);
            }

            driverRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
