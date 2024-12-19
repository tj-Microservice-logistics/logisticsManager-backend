package com.maxrayyy.transportservice.Pojo;

import jakarta.persistence.*;

@Entity
@Table(name = "route_warehouses")
public class RouteWarehouses {

    public enum ArrivalStatus {
        NOT_ARRIVED("未到达"),
        ARRIVED("已到达");

        private final String description;

        ArrivalStatus(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "route_warhouses_id")
    private Integer routeWarehousesId;

    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route;

    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    @Column(name = "sequence")
    private Integer sequence;

    @Enumerated(EnumType.STRING)
    @Column(name = "arrival_status")
    private ArrivalStatus arrivalStatus;

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

    public ArrivalStatus getArrivalStatus() {
        return arrivalStatus;
    }

    public void setArrivalStatus(ArrivalStatus arrivalStatus) {
        this.arrivalStatus = arrivalStatus;
    }

    @Override
    public String toString() {
        return "RouteWarehouses{" +
                "routeWarehousesId=" + routeWarehousesId +
                ", route=" + route +
                ", warehouse=" + warehouse +
                ", sequence=" + sequence +
                ", arrivalStatus=" + arrivalStatus +
                '}';
    }
}
