package com.maxrayyy.transportservice.Pojo;

import jakarta.persistence.*;

import java.sql.Timestamp;


@Entity
@Table(name = "waybill")
public class Waybill {

    public enum TransportStatus {
        IN_TRANSIT("运输中"),
        COMPLETED("已完成");

        private final String desc;

        private TransportStatus(String desc) {
            this.desc = desc;
        }

        public String getDesc() {
            return desc;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "waybill_id")
    private Integer waybillId;

    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route;

    @Column(name = "vehicle_plate_number")
    private String vehiclePlateNumber;

    @Column(name = "driver_id")
    private Integer driverId;

    @Enumerated(EnumType.STRING)
    @Column(name = "transport_status")
    private TransportStatus transportStatus;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    public Integer getWaybillId() {
        return waybillId;
    }

    public void setWaybillId(Integer waybillId) {
        this.waybillId = waybillId;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public String getVehiclePlateNumber() {
        return vehiclePlateNumber;
    }

    public void setVehiclePlateNumber(String vehiclePlateNumber) {
        this.vehiclePlateNumber = vehiclePlateNumber;
    }

    public Integer getDriverId() {
        return driverId;
    }

    public void setDriverId(Integer driverId) {
        this.driverId = driverId;
    }

    public TransportStatus getTransportStatus() {
        return transportStatus;
    }

    public void setTransportStatus(TransportStatus transportStatus) {
        this.transportStatus = transportStatus;
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
        return "Waybill{" +
                "waybillId=" + waybillId +
                ", route=" + route +
                ", vehiclePlateNumber='" + vehiclePlateNumber + '\'' +
                ", driverId=" + driverId +
                ", transportStatus=" + transportStatus +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
