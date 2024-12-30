package com.maxrayyy.storageservice.controller;

import com.maxrayyy.storageservice.entity.Driver;
import com.maxrayyy.storageservice.entity.Warehouse;
import com.maxrayyy.storageservice.repository.DriverRepository;
import com.maxrayyy.storageservice.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/drivers")
public class DriverController {

    @Autowired
    DriverRepository driverRepository;

    @Autowired
    WarehouseRepository warehouseRepository;


    // 获取所有司机
    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAllDrivers() {
        List<Map<String, Object>> driversWithWarehouse = driverRepository.findAll()
                .stream()
                .map(driver -> {
                    // 创建一个Map，用于存储司机信息和仓库名称
                    Map<String, Object> driverMap = new HashMap<>();
                    driverMap.put("driverId", driver.getDriverId());
                    driverMap.put("fullName", driver.getFullName());
                    driverMap.put("contactNumber", driver.getContactNumber());
                    driverMap.put("available", driver.getIsAvailable());

                    // 查询仓库名称
                    String warehouseName = driver.getWarehouse().getWarehouseName();
                    driverMap.put("warehouseName", warehouseName);

                    return driverMap;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(driversWithWarehouse);
    }

    // 根据ID获取单个司机
    @GetMapping("/{driverId}")
    public ResponseEntity<Driver> getDriverById(@PathVariable Integer driverId) {
        return driverRepository.findById(driverId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 添加新司机
    @PostMapping
    public ResponseEntity<Driver> createDriver(@RequestBody Driver driver) {

        // 使用 warehouseId 查找 Warehouse
        if (driver.getWarehouseId() != null) {
            Warehouse warehouse = warehouseRepository.findById(driver.getWarehouseId()).orElse(null);
            driver.setWarehouse(warehouse); // 设置关联的 Warehouse
        }

        driver.setIsAvailable(true); // 默认设置为可用
        return ResponseEntity.ok(driverRepository.save(driver));
    }


    // 更新司机信息
    @PutMapping("/{driverId}")
    public ResponseEntity<Driver> updateDriver(@PathVariable Integer driverId, @RequestBody Driver driver) {
        return driverRepository.findById(driverId).map(existingDriver -> {

            existingDriver.setFullName(driver.getFullName());
            existingDriver.setContactNumber(driver.getContactNumber());
            existingDriver.setWarehouseId(driver.getWarehouseId());
            existingDriver.setWarehouse(warehouseRepository.findById(driver.getWarehouseId())
                    .orElseThrow(() -> new RuntimeException("No available warehouses in the specified warehouse.")));
            existingDriver.setIsAvailable(driver.getIsAvailable());

            return ResponseEntity.ok(driverRepository.save(existingDriver));
        }).orElse(ResponseEntity.notFound().build());
    }

    // 删除司机
    @DeleteMapping("/{driverId}")
    public ResponseEntity<Void> deleteDriver(@PathVariable Integer driverId) {
        if (driverRepository.existsById(driverId)) {
            driverRepository.deleteById(driverId);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
