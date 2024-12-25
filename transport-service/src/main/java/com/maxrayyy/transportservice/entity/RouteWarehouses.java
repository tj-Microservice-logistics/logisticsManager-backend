package com.maxrayyy.transportservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "route_warehouses")
public class RouteWarehouses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "route_warehouses_id")
    private Integer routeWarehousesId;

    @ManyToOne
    @JoinColumn(name = "route_id", referencedColumnName = "route_id")
    private Route route;

    @ManyToOne
    @JoinColumn(name = "warehouse_id", referencedColumnName = "warehouse_id")
    private Warehouse warehouse;

    @Column(name = "sequence")
    private Integer sequence;

    @Column(name = "arrival")
    private boolean arrival = false;

    @Override
    public String toString() {
        return "RouteWarehouses{" +
                "routeWarehousesId=" + routeWarehousesId +
                ", route=" + route +
                ", warehouse=" + warehouse +
                ", sequence=" + sequence +
                ", arrival=" + arrival +
                '}';
    }
}
