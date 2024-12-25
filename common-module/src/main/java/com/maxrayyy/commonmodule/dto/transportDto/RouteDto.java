package com.maxrayyy.commonmodule.dto.transportDto;

import lombok.Data;

@Data
// RouteDto 用于请求体，创建新 Route 时传入数据
// 用于创建新 Route 时，结合 ResponseMessage<RouteDto> 获取返回信息
public class RouteDto {

    private Integer routeId;
    private Integer orderId;
    private Integer startWarehouseId;
    private Integer endWarehouseId;
    private Integer totalCost;
    private Double cargoWeight;


}
