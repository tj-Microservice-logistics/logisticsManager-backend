package com.maxrayyy.transportservice.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RouteDto {

    private Integer routeId;
    private Integer orderId;
    private Integer startWarehouse;
    private Integer endWarehouse;
    private Integer totalCost;
    private Double cargoWeight;

    @Override
    public String toString() {
        return "RouteDto{" +
                "routeId=" + routeId +
                ", orderId=" + orderId +
                ", startWarehouse=" + startWarehouse +
                ", endWarehouse=" + endWarehouse +
                ", totalCost=" + totalCost +
                ", cargoWeight=" + cargoWeight +
                '}';
    }
}
