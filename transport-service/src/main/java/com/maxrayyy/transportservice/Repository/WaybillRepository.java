package com.maxrayyy.transportservice.Repository;

import com.maxrayyy.transportservice.Pojo.Warehouse;
import com.maxrayyy.transportservice.Pojo.Waybill;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WaybillRepository extends CrudRepository<Waybill, Integer> {
    Iterable<Waybill> findByStartAndEnd(Warehouse startWarehouse, Warehouse endWarehouse);
}
