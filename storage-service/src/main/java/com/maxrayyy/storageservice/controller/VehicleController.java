package com.maxrayyy.storageservice.controller;

import com.maxrayyy.storageservice.dto.VehicleAndDriver;
import com.maxrayyy.storageservice.entity.Vehicle;
import com.maxrayyy.storageservice.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    @Autowired
    VehicleService vehicleService;

    /**
     * 获取所有车辆及其详细信息（包括仓库信息）
     */
    @GetMapping
    public ResponseEntity<List<Vehicle>> getAllVehicles() {
        List<Vehicle> vehicleList = vehicleService.getAll();
        return ResponseEntity.ok(vehicleList);
    }

    /**
     * 发车时根据仓库 ID 调空闲车辆和空闲司机并分配
     */
    @GetMapping("/available")
    public ResponseEntity<?> getAvailableVehicleAndDriver(
            @RequestParam Integer startWarehouseId,
            @RequestParam Integer endWarehouseId,
            @RequestParam Integer vehicleId) {
        VehicleAndDriver vehicleAndDriver = vehicleService.assignVehicleAndDriver(startWarehouseId, endWarehouseId, vehicleId);
        return ResponseEntity.ok(vehicleAndDriver);
    }

    /**
     * 到达时根据车牌号与司机更新空闲状态
     */
    @PutMapping("/arrive")
    public ResponseEntity<?> arriveVehicle(
            @RequestParam Integer vehicleId,
            @RequestParam String driverName) {
        VehicleAndDriver vehicleAndDriver = vehicleService.arriveVehicleAndDriver(vehicleId, driverName);
        return ResponseEntity.ok(vehicleAndDriver);
    }

    /**
     * 添加新车辆
     */
    @PostMapping
    public ResponseEntity<?> createVehicle(@RequestBody Vehicle vehicle) {
        Vehicle vehicleNew = vehicleService.add(vehicle);
        return ResponseEntity.ok(vehicleNew);
    }

    /**
     * 更新车辆信息
     */
    @PutMapping("/{vehicleId}")
    public ResponseEntity<?> updateVehicle(@PathVariable Integer vehicleId, @RequestBody Vehicle vehicle) {
       Vehicle updatedvehicle = vehicleService.update(vehicleId, vehicle);
       return ResponseEntity.ok(updatedvehicle);
    }

    /**
     * 删除车辆
     */
    @DeleteMapping("/{vehicleId}")
    public ResponseEntity<Map<String, Object>> deleteVehicle(@PathVariable Integer vehicleId) {
        Map<String, Object> response = vehicleService.delete(vehicleId);

        // 如果删除成功
        if (response.containsKey("message")) {
            return ResponseEntity.ok(response);
        } else {
            // 如果车辆不存在或出错
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
