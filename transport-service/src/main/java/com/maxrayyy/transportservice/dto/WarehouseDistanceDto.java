package com.maxrayyy.transportservice.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class WarehouseDistanceDto {

    private Integer warehouseDistanceId;
    private Integer warehouse1Id;
    private Integer warehouse2Id;
    private Double distance;
    private Integer cost;

    @Override
    public String toString() {
        return "WarehouseDistanceDto{" +
                "warehouseDistanceId=" + warehouseDistanceId +
                ", warehouse1Id=" + warehouse1Id +
                ", warehouse2Id=" + warehouse2Id +
                ", distance=" + distance +
                ", cost=" + cost +
                '}';
    }
}
