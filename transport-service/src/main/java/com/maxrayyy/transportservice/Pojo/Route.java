package com.maxrayyy.transportservice.Pojo;

import jakarta.persistence.*;

import java.security.Timestamp;

@Entity
@Table(name = "route")
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "route_id")
    private Integer routeId;

    @Column(name = "order_id")
    private Integer orderId;

    @Column(name = "start_warehouse")
    private String startWarehouse;

    @Column(name = "end_warehouse")
    private String endWarehouse;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    public Integer getRouteId() {
        return routeId;
    }

    public void setRouteId(Integer routeId) {
        this.routeId = routeId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getStartWarehouse() {
        return startWarehouse;
    }

    public void setStartWarehouse(String startWarehouse) {
        this.startWarehouse = startWarehouse;
    }

    public String getEndWarehouse() {
        return endWarehouse;
    }

    public void setEndWarehouse(String endWarehouse) {
        this.endWarehouse = endWarehouse;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Route{" +
                "routeId=" + routeId +
                ", orderId=" + orderId +
                ", startWarehouse='" + startWarehouse + '\'' +
                ", endWarehouse='" + endWarehouse + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
