package com.maxrayyy.transportservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
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

    @Column(name = "order_number")
    private String orderNumber;

    @Column(name = "total_cost")
    private Integer totalCost;

    @Column(name = "cargo_weight")
    private Double cargoWeight;

    @Override
    public String toString() {
        return "Route{" +
                "routeId=" + routeId +
                ", startWarehouse=" + startWarehouse +
                ", endWarehouse=" + endWarehouse +
                ", orderNumber=" + orderNumber +
                ", totalCost=" + totalCost +
                ", cargoWeight=" + cargoWeight +
                '}';
    }
}
