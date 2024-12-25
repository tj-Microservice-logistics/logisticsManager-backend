package com.maxrayyy.commonmodule.dto.transportDto;

import lombok.Data;

@Data
// 用于结合 ResponseMessage<RouteWarehousesDto> 获取返回信息
public class RouteWarehousesDto {

    private Integer routeWarehousesId;
    private Integer routeId;
    private Integer warehouseId;
    private Integer sequence;
    private boolean arrival;

}
