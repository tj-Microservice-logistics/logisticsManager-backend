package com.maxrayyy.transportservice.Pojo;

import jakarta.persistence.*;

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

    public Integer getRouteWarehousesId() {
        return routeWarehousesId;
    }

    public void setRouteWarehousesId(Integer routeWarehousesId) {
        this.routeWarehousesId = routeWarehousesId;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    @Override
    public String toString() {
        return "RouteWarehouses{" +
                "routeWarehousesId=" + routeWarehousesId +
                ", route=" + route +
                ", warehouse=" + warehouse +
                ", sequence=" + sequence +
                '}';
    }
}
