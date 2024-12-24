package com.maxrayyy.transportservice.Repository;

import com.maxrayyy.transportservice.Pojo.Warehouse;
import com.maxrayyy.transportservice.Pojo.WarehouseDistance;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface WarehouseDistanceRepository extends CrudRepository<WarehouseDistance, Integer> {

    boolean existsByWarehouse1AndWarehouse2(Warehouse warehouse1, Warehouse warehouse2);

    Optional<WarehouseDistance> findByWarehouse1AndWarehouse2(Warehouse start, Warehouse end);
}
