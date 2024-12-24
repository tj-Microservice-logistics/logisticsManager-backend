package com.maxrayyy.reportservice.model.dto;

import lombok.Data;
import java.util.List;

@Data
public class StatisticsResponseDto {
    private List<StatisticsDataDto> data;
    private StatisticsSummaryDto summary;
}