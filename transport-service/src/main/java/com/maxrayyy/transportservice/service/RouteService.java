package com.maxrayyy.transportservice.service;

import com.maxrayyy.commonmodule.dto.transportDto.WarehouseDistanceDto;
import com.maxrayyy.transportservice.entity.Route;
import com.maxrayyy.transportservice.entity.RouteWarehouses;
import com.maxrayyy.transportservice.entity.Warehouse;
import com.maxrayyy.transportservice.entity.WarehouseDistance;
import com.maxrayyy.transportservice.repository.RouteRepository;
import com.maxrayyy.transportservice.repository.RouteWarehousesRepository;
import com.maxrayyy.transportservice.repository.WarehouseDistanceRepository;
import com.maxrayyy.transportservice.repository.WarehouseRepository;
import com.maxrayyy.commonmodule.dto.transportDto.RouteDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RouteService implements IRouteService {

    @Autowired
    RouteRepository routeRepository;

    @Autowired
    WarehouseDistanceRepository warehouseDistanceRepository;

    @Autowired
    RouteWarehousesRepository routeWarehousesRepository;

    @Autowired
    WarehouseRepository warehouseRepository;

    @Autowired
    private WaybillService waybillService;

    @Override
    public RouteDto add(RouteDto routeDto) {

        // step1：找到最短路径
        List<Integer> shortestRoute = findShortestRoute(routeDto.getStartWarehouseId(), routeDto.getEndWarehouseId());

        // step2：创建 Route 对象
        Route route = new Route();

        Warehouse startWarehouse = warehouseRepository.findById(routeDto.getStartWarehouseId())
                .orElseThrow(() -> new IllegalArgumentException("无法找到起点仓库！"));
        Warehouse endWarehouse = warehouseRepository.findById(routeDto.getEndWarehouseId())
                .orElseThrow(() -> new IllegalArgumentException("无法找到终点仓库！"));

        BeanUtils.copyProperties(routeDto, route);
        route.setStartWarehouse(startWarehouse);
        route.setEndWarehouse(endWarehouse);

        routeRepository.save(route);

        // step3：创建 RouteWarehouses 保存最短路径
        generateRouteWarehouses(shortestRoute, route);

        // step4：创建 Waybills 并保存
        waybillService.generateWaybills(shortestRoute, route);

        // step5：返回 RouteDto
        RouteDto resultDto = new RouteDto();
        BeanUtils.copyProperties(route, resultDto);
        resultDto.setStartWarehouseId(startWarehouse.getWarehouseId());
        resultDto.setEndWarehouseId(endWarehouse.getWarehouseId());

        return resultDto;

    }

    @Override
    public Map<Integer, List<WarehouseDistanceDto>> buildGraph() {

        Iterable<WarehouseDistance> distances = warehouseDistanceRepository.findAll();
        Map<Integer, List<WarehouseDistanceDto>> warehouseDistanceMap = new HashMap<>();

        for(WarehouseDistance distance : distances){
            int warehouse1Id = distance.getWarehouse1().getWarehouseId();
            int warehouse2Id = distance.getWarehouse2().getWarehouseId();

            // 将仓库和路径添加到图中，无向边，两个方向均添加
            WarehouseDistanceDto distanceDto = new WarehouseDistanceDto();
            BeanUtils.copyProperties(distance, distanceDto);
            distanceDto.setWarehouse1Id(warehouse1Id);
            distanceDto.setWarehouse2Id(warehouse2Id);

            warehouseDistanceMap.computeIfAbsent(warehouse1Id, k -> new ArrayList<>()).add(distanceDto);
            warehouseDistanceMap.computeIfAbsent(warehouse2Id, k -> new ArrayList<>()).add(distanceDto);
        }

        return warehouseDistanceMap;

    }

    @Override
    public List<Integer> findShortestRoute(Integer startWarehouseId, Integer endWarehouseId) {

        Map<Integer, List<WarehouseDistanceDto>> graph = buildGraph();
        Map<Integer, Double> distances = new HashMap<>();
        Map<Integer, Integer> previous = new HashMap<>();
        PriorityQueue<Integer> routeQueue = new PriorityQueue<>(Comparator.comparingDouble(distances::get));


        // 初始化
        for(Integer warehouseId : graph.keySet()){
            distances.put(warehouseId, Double.MAX_VALUE);
            previous.put(warehouseId, null);
        }
        distances.put(startWarehouseId, 0.0);
        routeQueue.add(startWarehouseId);

        // Dijkstra 算法
        while(!routeQueue.isEmpty()){
            int currentWarehouseId = routeQueue.poll();

            for(WarehouseDistanceDto neighbor : graph.get(currentWarehouseId)){
                int neighborId = (neighbor.getWarehouse1Id() == currentWarehouseId)
                        ? neighbor.getWarehouse2Id()
                        : neighbor.getWarehouse1Id();

                double newDistance = distances.get(currentWarehouseId) + neighbor.getDistance();
                if(newDistance < distances.get(neighborId)){
                    distances.put(neighborId, newDistance);
                    previous.put(neighborId, currentWarehouseId);
                    routeQueue.add(neighborId);
                }
            }
        }

        // 回溯路径
        List<Integer> shortestRoute = new ArrayList<>();
        Integer currentWarehouseId = endWarehouseId;
        while(currentWarehouseId != null){
            shortestRoute.add(currentWarehouseId);
            currentWarehouseId = previous.get(currentWarehouseId);
        }
        Collections.reverse(shortestRoute);

        return shortestRoute;

    }

    @Override
    public  void generateRouteWarehouses(List<Integer> shortestRoute, Route route) {

        int sequence = 1;

        for(Integer warehouseId : shortestRoute){
            Warehouse warehouse = warehouseRepository.findById(warehouseId).orElseThrow(() -> new IllegalArgumentException("未找到 Id : " + warehouseId + "的仓库！"));

            RouteWarehouses routeWarehouses = new RouteWarehouses();
            routeWarehouses.setRoute(route);
            routeWarehouses.setWarehouse(warehouse);
            routeWarehouses.setSequence(sequence++);
            routeWarehousesRepository.save(routeWarehouses);
        }
    }
}
