package com.maxrayyy.transportservice.repository;

import com.maxrayyy.transportservice.entity.Route;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRepository extends CrudRepository<Route, Integer> {
    Route findByOrderNumber(String orderNumber);
}
