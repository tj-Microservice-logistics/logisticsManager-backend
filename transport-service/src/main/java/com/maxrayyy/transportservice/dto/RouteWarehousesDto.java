package com.maxrayyy.transportservice.dto;

public class RouteWarehousesDto {

    private Integer routeWarehousesId;
    private Integer routeId;
    private Integer warehouseId;
    private Integer sequence;

    public Integer getRouteWarehousesId() {
        return routeWarehousesId;
    }

    public void setRouteWarehousesId(Integer routeWarehousesId) {
        this.routeWarehousesId = routeWarehousesId;
    }

    public Integer getRouteId() {
        return routeId;
    }

    public void setRouteId(Integer routeId) {
        this.routeId = routeId;
    }

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    @Override
    public String toString() {
        return "RouteWarehousesDto{" +
                "routeWarehousesId=" + routeWarehousesId +
                ", routeId=" + routeId +
                ", warehouseId=" + warehouseId +
                ", sequence=" + sequence +
                '}';
    }
}
