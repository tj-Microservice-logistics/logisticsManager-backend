package com.maxrayyy.transportservice.dto;

import java.security.Timestamp;

public class RouteDto {

    private Integer routeId;
    private Integer startWarehouse;
    private Integer endWarehouse;
    private Integer totalCost;
    private Double cargoWeight;

    public Double getCargoWeight() {
        return cargoWeight;
    }

    public void setCargoWeight(Double cargoWeight) {
        this.cargoWeight = cargoWeight;
    }

    public Integer getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Integer totalCost) {
        this.totalCost = totalCost;
    }

    public Integer getStartWarehouse() {
        return startWarehouse;
    }

    public Integer getEndWarehouse() {
        return endWarehouse;
    }

    public void setStartWarehouse(Integer startWarehouse) {
        this.startWarehouse = startWarehouse;
    }

    public void setEndWarehouse(Integer endWarehouse) {
        this.endWarehouse = endWarehouse;
    }

    public Integer getRouteId() {
        return routeId;
    }

    public void setRouteId(Integer routeId) {
        this.routeId = routeId;
    }

    @Override
    public String toString() {
        return "RouteDto{" +
                "routeId=" + routeId +
                ", startWarehouse=" + startWarehouse +
                ", endWarehouse=" + endWarehouse +
                ", totalCost=" + totalCost +
                ", cargoWeight=" + cargoWeight +
                '}';
    }
}
