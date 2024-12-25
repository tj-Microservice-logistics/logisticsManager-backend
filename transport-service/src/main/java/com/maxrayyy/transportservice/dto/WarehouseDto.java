package com.maxrayyy.transportservice.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class WarehouseDto {

    private Integer warehouseId;
    private String warehouseName;


    @Override
    public String toString() {
        return "WarehouseDto{" +
                "warehouseId=" + warehouseId +
                ", warehouseName='" + warehouseName + '\'' +
                '}';
    }
}
