package com.maxrayyy.commonmodule.dto.transportDto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class WaybillDto {

    private Integer waybillId;
    private Integer routeId;
    private String orderNumber;
    private String vehiclePlateNumber;
    private String driverName;
    private String transportStatus;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String startWarehouseName;
    private String endWarehouseName;
    private Double cargoWeight;

}
