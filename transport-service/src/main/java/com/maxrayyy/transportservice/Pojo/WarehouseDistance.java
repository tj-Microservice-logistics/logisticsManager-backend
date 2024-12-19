package com.maxrayyy.transportservice.Pojo;

import jakarta.persistence.*;

@Entity
@Table(name = "warehouse_distance", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"warehouse_id_1", "warehouse_id_2"})
})
public class WarehouseDistance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "warehouse_distance_id")
    private Integer warehouseDistanceId;

    @ManyToOne
    @JoinColumn(name = "warehouse_id_1", referencedColumnName = "warehouse_id", nullable = false)
    private Warehouse warehouse1;

    @ManyToOne
    @JoinColumn(name = "warehouse_id_2", referencedColumnName = "warehouse_id", nullable = false)
    private Warehouse warehouse2;

    @Column(name = "distance", nullable = false)
    private Double distance;

    @Column(name = "cost")
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

    public Warehouse getWarehouse1() {
        return warehouse1;
    }

    public void setWarehouse1(Warehouse warehouse1) {
        this.warehouse1 = warehouse1;
    }

    public Warehouse getWarehouse2() {
        return warehouse2;
    }

    public void setWarehouse2(Warehouse warehouse2) {
        this.warehouse2 = warehouse2;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "WarehouseDistance{" +
                "warehouseDistanceId=" + warehouseDistanceId +
                ", warehouse1=" + warehouse1 +
                ", warehouse2=" + warehouse2 +
                ", distance=" + distance +
                ", cost=" + cost +
                '}';
    }
}
