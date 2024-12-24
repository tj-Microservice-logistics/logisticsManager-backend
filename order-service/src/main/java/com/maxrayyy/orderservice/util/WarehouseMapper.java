package com.maxrayyy.orderservice.util;

import java.util.HashMap;
import java.util.Map;

public class WarehouseMapper {
    private static final Map<String, Integer> PLACE_TO_WAREHOUSE = new HashMap<>();
    private static final Map<Integer, String> WAREHOUSE_TO_PLACE = new HashMap<>();

    static {
        PLACE_TO_WAREHOUSE.put("Hefei", 1);
        PLACE_TO_WAREHOUSE.put("Nanjing", 2);
        PLACE_TO_WAREHOUSE.put("Shanghai", 3);
        PLACE_TO_WAREHOUSE.put("Hangzhou", 4);
        PLACE_TO_WAREHOUSE.put("Nanchang", 5);

        WAREHOUSE_TO_PLACE.put(1, "Hefei");
        WAREHOUSE_TO_PLACE.put(2, "Nanjing");
        WAREHOUSE_TO_PLACE.put(3, "Shanghai");
        WAREHOUSE_TO_PLACE.put(4, "Hangzhou");
        WAREHOUSE_TO_PLACE.put(5, "Nanchang");
    }

    public static Integer getWarehouseId(String place) {
        return PLACE_TO_WAREHOUSE.get(place);
    }

    public static String getPlace(Integer warehouseId) {
        return WAREHOUSE_TO_PLACE.get(warehouseId);
    }
} 