package com.maxrayyy.storageservice.controller;

import lombok.Data;

@Data
public class DriverDto {
    private String fullName;
    private String contactNumber;
    private Integer warehouseId;
    private Boolean available;
}