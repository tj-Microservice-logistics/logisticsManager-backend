package com.maxrayyy.transportservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
public class WaybillDto {

    private Integer waybillId;
    private Integer routeId;
    private Integer orderId;
    private String vehiclePlateNumber;
    private String driverName;
    private String transportStatus;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Integer startId;
    private Integer endId;
    private Double cargoWeight;

    @Override
    public String toString() {
        return "WaybillDto{" +
                "waybillId=" + waybillId +
                ", routeId=" + routeId +
                ", orderId=" + orderId +
                ", vehiclePlateNumber='" + vehiclePlateNumber + '\'' +
                ", driverName='" + driverName + '\'' +
                ", transportStatus='" + transportStatus + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", startId=" + startId +
                ", endId=" + endId +
                ", cargoWeight=" + cargoWeight +
                '}';
    }
}
