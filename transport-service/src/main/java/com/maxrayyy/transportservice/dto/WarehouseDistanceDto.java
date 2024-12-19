package com.maxrayyy.transportservice.dto;

public class WarehouseDistanceDto {

    private Integer warehouseDistanceId;
    private Integer warehouse1Id;
    private Integer warehouse2Id;
    private Double distance;
    private Integer cost;

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public Integer getWarehouseDistanceId() {
        return warehouseDistanceId;
    }

    public void setWarehouseDistanceId(Integer warehouseDistanceId) {
        this.warehouseDistanceId = warehouseDistanceId;
    }

    public Integer getWarehouse1Id() {
        return warehouse1Id;
    }

    public void setWarehouse1Id(Integer warehouse1Id) {
        this.warehouse1Id = warehouse1Id;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Integer getWarehouse2Id() {
        return warehouse2Id;
    }

    public void setWarehouse2Id(Integer warehouse2Id) {
        this.warehouse2Id = warehouse2Id;
    }

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
