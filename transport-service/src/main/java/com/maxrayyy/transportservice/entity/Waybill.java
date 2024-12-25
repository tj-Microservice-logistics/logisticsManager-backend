package com.maxrayyy.transportservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
@Entity
@Table(name = "waybill")
public class Waybill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "waybill_id")
    private Integer waybillId;

    @ManyToOne
    @JoinColumn(name = "route_id", referencedColumnName = "route_id")
    private Route route;

    @Column(name = "order_id")
    private Integer orderId;

    @Column(name = "vehicle_plate_number")
    private String vehiclePlateNumber;

    @Column(name = "driver_name")
    private String driverName;

    @Column(name = "transport_status")
    private String transportStatus = "待发车";

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

    @Override
    public String toString() {
        return "Waybill{" +
                "waybillId=" + waybillId +
                ", route=" + route +
                ", orderId=" + orderId +
                ", vehiclePlateNumber='" + vehiclePlateNumber + '\'' +
                ", driverName='" + driverName + '\'' +
                ", transportStatus='" + transportStatus + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", start=" + start +
                ", end=" + end +
                ", cargoWeight=" + cargoWeight +
                '}';
    }
}
