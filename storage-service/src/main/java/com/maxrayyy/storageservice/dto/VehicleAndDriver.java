package com.maxrayyy.storageservice.dto;

import com.maxrayyy.storageservice.entity.Driver;
import com.maxrayyy.storageservice.entity.Vehicle;
import lombok.Data;

@Data
public class VehicleAndDriver {
    private Vehicle vehicle;
    private Driver driver;
}
