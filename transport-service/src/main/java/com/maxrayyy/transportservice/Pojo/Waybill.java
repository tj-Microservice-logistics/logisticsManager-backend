package com.maxrayyy.transportservice.Pojo;

import jakarta.persistence.*;

import java.sql.Timestamp;


@Entity
@Table(name = "waybill")
public class Waybill {

    public enum TransportStatus {
        WAITING("待发车"),
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
    @JoinColumn(name = "route_id", referencedColumnName = "route_id")
    private Route route;

    @Column(name = "vehicle_plate_number")
    private String vehiclePlateNumber;

    @Column(name = "driver_name")
    private String driverName;

    @Enumerated(EnumType.STRING)
    @Column(name = "transport_status")
    private TransportStatus transportStatus = TransportStatus.WAITING;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @ManyToOne
    @JoinColumn(name = "start", referencedColumnName = "warehouse_id")
    private Warehouse start;

    @ManyToOne
    @JoinColumn(name = "end", referencedColumnName = "warehouse_id")
    private Warehouse end;

    @Column(name = "cargo_weight")
    private Double cargoWeight;

    public Double getCargoWeight() {
        return cargoWeight;
    }

    public void setCargoWeight(Double cargoWeight) {
        this.cargoWeight = cargoWeight;
    }

    public Warehouse getStart() {
        return start;
    }

    public void setStart(Warehouse start) {
        this.start = start;
    }

    public Warehouse getEnd() {
        return end;
    }

    public void setEnd(Warehouse end) {
        this.end = end;
    }

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

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    @Override
    public String toString() {
        return "Waybill{" +
                "waybillId=" + waybillId +
                ", route=" + route +
                ", vehiclePlateNumber='" + vehiclePlateNumber + '\'' +
                ", driverName='" + driverName + '\'' +
                ", transportStatus=" + transportStatus +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", start=" + start +
                ", end=" + end +
                ", cargoWeight=" + cargoWeight +
                '}';
    }
}
