package com.maxrayyy.storageservice.service;

import com.maxrayyy.commonmodule.dto.transportDto.WaybillDto;
import com.maxrayyy.storageservice.dto.VehicleAndDriver;
import com.maxrayyy.storageservice.entity.Driver;
import com.maxrayyy.storageservice.entity.Vehicle;
import com.maxrayyy.storageservice.entity.Warehouse;
import com.maxrayyy.storageservice.feignClient.OrderServiceClient;
import com.maxrayyy.storageservice.feignClient.TransportServiceClient;
import com.maxrayyy.storageservice.repository.DriverRepository;
import com.maxrayyy.storageservice.repository.VehicleRepository;
import com.maxrayyy.storageservice.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VehicleService {

    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    TransportServiceClient transportServiceClient;

    @Autowired
    OrderServiceClient orderServiceClient;

    public List<Vehicle> getAll() {
        return vehicleRepository.findAll();
    }

    public Vehicle add(Vehicle vehicle) {
        if (vehicle.getWarehouseId() != null) {
            Warehouse warehouse = warehouseRepository.findById(vehicle.getWarehouseId())
                    .orElseThrow(() -> new RuntimeException("No available warehouses in the specified warehouse."));
            vehicle.setWarehouse(warehouse);
        }
        return vehicleRepository.save(vehicle);
    }

    public Vehicle update(Integer vehicleId, Vehicle vehicle) {
        return vehicleRepository.findById(vehicleId)
                .map(existingVehicle -> {

                    existingVehicle.setVehicleType(vehicle.getVehicleType());
                    existingVehicle.setStatus(vehicle.getStatus());
                    existingVehicle.setWarehouseId(vehicle.getWarehouseId());
                    existingVehicle.setWarehouse(warehouseRepository.findById(vehicle.getWarehouseId())
                            .orElseThrow(() -> new RuntimeException("No available warehouses in the specified warehouse.")));
                    existingVehicle.setLicensePlate(vehicle.getLicensePlate());

                    return vehicleRepository.save(existingVehicle);
                })
                .orElseThrow(() -> new RuntimeException("Vehicle not found with id: " + vehicleId));
    }

    public Map<String, Object> delete(Integer vehicleId) {
        Map<String, Object> response = new HashMap<>();
        try {
            // 检查车辆是否存在
            if (vehicleRepository.existsById(vehicleId)) {
                vehicleRepository.deleteById(vehicleId);
                response.put("message", "Vehicle deleted successfully.");
                response.put("vehicleId", vehicleId);
            } else {
                response.put("error", "Vehicle not found with id: " + vehicleId);
            }
            return response;
        } catch (Exception e) {
            response.put("error", "An error occurred while deleting the vehicle: " + e.getMessage());
            return response;
        }
    }

    public VehicleAndDriver assignVehicleAndDriver(Integer startWarehouseId, Integer endWarehouseId, Integer vehicleId) {

        VehicleAndDriver vehicleAndDriver = new VehicleAndDriver();

        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("No available vehicles in the specified warehouse."));

        // 自动匹配一个该仓库空闲司机
        Driver driver = driverRepository.findFirstByWarehouseIdAndIsAvailableTrue(startWarehouseId)
                .orElseThrow(() -> new RuntimeException("No available drivers in the specified warehouse."));

        vehicle.setDriverName(driver.getFullName());
        vehicle.setStatus(false);
        driver.setIsAvailable(false);

        vehicleRepository.save(vehicle);
        driverRepository.save(driver);

        vehicleAndDriver.setVehicle(vehicle);
        vehicleAndDriver.setDriver(driver);

        WaybillDto newWaybillDto = new WaybillDto();
        newWaybillDto.setDriverName(driver.getFullName());
        newWaybillDto.setVehiclePlateNumber(vehicle.getLicensePlate());
        newWaybillDto.setTransportStatus("运输中");



        List<WaybillDto> waybillDtos = transportServiceClient.getRouteWaybills(startWarehouseId, endWarehouseId).getData();

        for (WaybillDto waybillDto : waybillDtos) {
            transportServiceClient.updateWaybill(waybillDto.getWaybillId(), newWaybillDto);
            orderServiceClient.updateDeliverStatus(waybillDto.getOrderNumber());
        }

        return vehicleAndDriver;
    }

    public VehicleAndDriver arriveVehicleAndDriver(Integer vehicleId, String driverName) {
        VehicleAndDriver vehicleAndDriver = new VehicleAndDriver();

        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("No available drivers in the specified warehouse."));

        Driver driver = driverRepository.findByFullName(driverName)
                .orElseThrow(() -> new RuntimeException("No available drivers in the specified warehouse."));

        vehicle.setDriverName(null);
        vehicle.setStatus(true);
        driver.setIsAvailable(true);

        vehicleRepository.save(vehicle);
        driverRepository.save(driver);

        vehicleAndDriver.setVehicle(vehicle);
        vehicleAndDriver.setDriver(driver);

        WaybillDto newWaybillDto = new WaybillDto();
        newWaybillDto.setTransportStatus("已到达");

        List<WaybillDto> waybillDtos = transportServiceClient.getVehicleWaybills(vehicle.getLicensePlate()).getData();

        transportServiceClient.updateArrival(vehicle.getLicensePlate());

        for (WaybillDto waybillDto : waybillDtos) {
            transportServiceClient.updateWaybill(waybillDto.getWaybillId(), newWaybillDto);
        }


        return vehicleAndDriver;
    }
}
