package com.maxrayyy.commonmodule.dto.transportDto;

import lombok.Data;

@Data
// 暂无 API 调用
public class WarehouseDistanceDto {

    private Integer warehouseDistanceId;
    private Integer warehouse1Id;
    private Integer warehouse2Id;
    private Double distance;
    private Integer cost;

}
