package com.maxrayyy.transportservice.Pojo;

import jakarta.persistence.*;

@Entity
@Table(name = "route")
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "route_id")
    private Integer routeId;

    @ManyToOne
    @JoinColumn(name = "start_warehouse", referencedColumnName = "warehouse_id")
    private Warehouse startWarehouse;

    @ManyToOne
    @JoinColumn(name = "end_warehouse", referencedColumnName = "warehouse_id")
    private Warehouse endWarehouse;

    @Column(name = "total_cost")
    private Integer totalCost;

    @Column(name = "cargo_weight")
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

    public Warehouse getStartWarehouse() {
        return startWarehouse;
    }

    public void setStartWarehouse(Warehouse startWarehouse) {
        this.startWarehouse = startWarehouse;
    }

    public Warehouse getEndWarehouse() {
        return endWarehouse;
    }

    public void setEndWarehouse(Warehouse endWarehouse) {
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
        return "Route{" +
                "routeId=" + routeId +
                ", startWarehouse=" + startWarehouse +
                ", endWarehouse=" + endWarehouse +
                ", totalCost=" + totalCost +
                ", cargoWeight=" + cargoWeight +
                '}';
    }
}
