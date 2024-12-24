package com.maxrayyy.transportservice.Repository;

import com.maxrayyy.transportservice.Pojo.Route;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRepository extends CrudRepository<Route, Integer> {
}
