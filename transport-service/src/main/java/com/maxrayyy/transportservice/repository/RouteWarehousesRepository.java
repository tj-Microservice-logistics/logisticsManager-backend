package com.maxrayyy.transportservice.repository;

import com.maxrayyy.transportservice.entity.RouteWarehouses;
import com.maxrayyy.transportservice.entity.Warehouse;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteWarehousesRepository extends CrudRepository<RouteWarehouses, Integer> {

    Iterable<RouteWarehouses> findByRouteRouteId(Integer routeId);

    List<RouteWarehouses> findByWarehouse(Warehouse warehouse);
}
