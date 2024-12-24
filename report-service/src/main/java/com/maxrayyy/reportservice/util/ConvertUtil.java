package com.maxrayyy.reportservice.util;

import com.maxrayyy.reportservice.model.entity.OrderRawData;
import com.maxrayyy.reportservice.model.excel.OrderExportData;
import org.springframework.beans.BeanUtils;
import java.util.List;
import java.util.stream.Collectors;

public class ConvertUtil {
    
    public static OrderExportData toExportData(OrderRawData rawData) {
        OrderExportData exportData = new OrderExportData();
        BeanUtils.copyProperties(rawData, exportData);
        return exportData;
    }
    
    public static List<OrderExportData> toExportDataList(List<OrderRawData> rawDataList) {
        return rawDataList.stream()
                .map(ConvertUtil::toExportData)
                .collect(Collectors.toList());
    }
} 