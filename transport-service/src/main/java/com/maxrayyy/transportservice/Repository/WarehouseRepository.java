package com.maxrayyy.transportservice.Repository;

import com.maxrayyy.transportservice.Pojo.Warehouse;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseRepository extends CrudRepository<Warehouse, Integer> {
}
