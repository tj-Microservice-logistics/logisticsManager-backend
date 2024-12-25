package com.maxrayyy.transportservice.repository;

import com.maxrayyy.transportservice.entity.Warehouse;
import com.maxrayyy.transportservice.entity.Waybill;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WaybillRepository extends CrudRepository<Waybill, Integer> {
    Iterable<Waybill> findByStartAndEnd(Warehouse startWarehouse, Warehouse endWarehouse);
}
