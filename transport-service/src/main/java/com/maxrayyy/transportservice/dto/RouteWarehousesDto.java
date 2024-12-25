package com.maxrayyy.transportservice.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RouteWarehousesDto {

    private Integer routeWarehousesId;
    private Integer routeId;
    private Integer warehouseId;
    private Integer sequence;
    private boolean arrival;

    @Override
    public String toString() {
        return "RouteWarehousesDto{" +
                "routeWarehousesId=" + routeWarehousesId +
                ", routeId=" + routeId +
                ", warehouseId=" + warehouseId +
                ", sequence=" + sequence +
                ", arrival=" + arrival +
                '}';
    }
}
