package com.maxrayyy.transportservice.Repository;

import com.maxrayyy.transportservice.Pojo.RouteWarehouses;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteWarehousesRepository extends CrudRepository<RouteWarehouses, Integer> {

    Iterable<RouteWarehouses> findByRouteRouteId(Integer routeId);
}
