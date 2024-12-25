package com.maxrayyy.commonmodule.dto.transportDto;

import lombok.Data;

@Data
// RouteDto 用于请求体，创建新 Route 时传入数据
// 用于创建新 Route 时，结合 ResponseMessage<RouteDto> 获取返回信息
public class RouteDto {

    private Integer routeId;
    private String orderNumber;
    private String startWarehouseName;
    private String endWarehouseName;
    private Integer totalCost;
    private Double cargoWeight;

}
