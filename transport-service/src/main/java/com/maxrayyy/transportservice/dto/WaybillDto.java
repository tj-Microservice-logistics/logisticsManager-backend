package com.maxrayyy.transportservice.dto;

import java.sql.Timestamp;

public class WaybillDto {

    private Integer waybillId;
    private Integer routeId;
    private String vehiclePlateNumber;
    private String driverName;
    private String transportStatus;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Integer startId;
    private Integer endId;
    private Double cargoWeight;

    public Double getCargoWeight() {
        return cargoWeight;
    }

    public void setCargoWeight(Double cargoWeight) {
        this.cargoWeight = cargoWeight;
    }

    public Integer getEndId() {
        return endId;
    }

    public void setEndId(Integer endId) {
        this.endId = endId;
    }

    public Integer getStartId() {
        return startId;
    }

    public void setStartId(Integer startId) {
        this.startId = startId;
    }

    public Integer getWaybillId() {
        return waybillId;
    }

    public void setWaybillId(Integer waybillId) {
        this.waybillId = waybillId;
    }

    public Integer getRouteId() {
        return routeId;
    }

    public void setRouteId(Integer routeId) {
        this.routeId = routeId;
    }

    public String getVehiclePlateNumber() {
        return vehiclePlateNumber;
    }

    public void setVehiclePlateNumber(String vehiclePlateNumber) {
        this.vehiclePlateNumber = vehiclePlateNumber;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getTransportStatus() {
        return transportStatus;
    }

    public void setTransportStatus(String transportStatus) {
        this.transportStatus = transportStatus;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "WaybillDto{" +
                "waybillId=" + waybillId +
                ", routeId=" + routeId +
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
