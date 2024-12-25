package com.maxrayyy.transportservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
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
