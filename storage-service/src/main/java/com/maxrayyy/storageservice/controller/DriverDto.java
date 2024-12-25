package com.maxrayyy.storageservice.controller;

import lombok.Data;

@Data
public class DriverDto {
    private Integer driverId; // 司机 ID
    private String fullName; // 司机姓名
    private String contactNumber; // 联系电话
    private Integer warehouseId; // 仓库 ID
    private Boolean available; // 是否空闲
    private Integer assignedVehicleId; // 分配车辆的 ID
    private String assignedVehicleLicensePlate; // 分配车辆的车牌号
    private String warehouseName; // 仓库名称
}
