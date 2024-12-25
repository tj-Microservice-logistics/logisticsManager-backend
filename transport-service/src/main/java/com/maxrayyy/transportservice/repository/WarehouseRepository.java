package com.maxrayyy.transportservice.repository;

import com.maxrayyy.transportservice.entity.Warehouse;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseRepository extends CrudRepository<Warehouse, Integer> {
}
