//package com.maxrayyy.reportservice.util;
//
//import com.maxrayyy.reportservice.model.entity.OrderRawData;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.springframework.core.io.ByteArrayResource;
//import org.springframework.core.io.FileSystemResource;
//import org.springframework.core.io.Resource;
//import org.springframework.stereotype.Component;
//
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.time.format.DateTimeFormatter;
//import java.util.List;
//
//@Slf4j
//@Component
//public class ExcelUtil {
//
//    public Resource generateExcel(List<OrderRawData> data, String filePath) {
//        try (Workbook workbook = new XSSFWorkbook()) {
//            Sheet sheet = workbook.createSheet("Order Data");
//
//            // 创建标题行
//            Row headerRow = sheet.createRow(0);
//            String[] headers = {"订单ID", "订单状态", "订单金额", "创建时间", "更新时间"};
//            for (int i = 0; i < headers.length; i++) {
//                Cell cell = headerRow.createCell(i);
//                cell.setCellValue(headers[i]);
//            }
//
//            // 填充数据
//            int rowNum = 1;
//            for (OrderRawData order : data) {
//                Row row = sheet.createRow(rowNum++);
//                row.createCell(0).setCellValue(order.getOrderId());
//                row.createCell(1).setCellValue(order.getOrderStatus());
//                row.createCell(2).setCellValue(order.getAmount().doubleValue());
//                row.createCell(3).setCellValue(
//                    order.getOrderCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
//                row.createCell(4).setCellValue(
//                    order.getOrderUpdateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
//            }
//
//            // 自动调整列宽
//            for (int i = 0; i < headers.length; i++) {
//                sheet.autoSizeColumn(i);
//            }
//
//            // 保存文件
//            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
//                workbook.write(fileOut);
//            }
//
//            return new FileSystemResource(filePath);
//
//        } catch (Exception e) {
//            log.error("Generate excel failed", e);
//            throw new RuntimeException("Generate excel failed", e);
//        }
//    }
//
//    private CellStyle createHeaderStyle(Workbook workbook) {
//        CellStyle headerStyle = workbook.createCellStyle();
//        // 设置背景色
//        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
//        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//        // 设置字体
//        Font headerFont = workbook.createFont();
//        headerFont.setBold(true);
//        headerStyle.setFont(headerFont);
//        // 设置边框
//        headerStyle.setBorderBottom(BorderStyle.THIN);
//        headerStyle.setBorderTop(BorderStyle.THIN);
//        headerStyle.setBorderLeft(BorderStyle.THIN);
//        headerStyle.setBorderRight(BorderStyle.THIN);
//        // 设置对齐方式
//        headerStyle.setAlignment(HorizontalAlignment.CENTER);
//        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
//
//        return headerStyle;
//    }
//
//    private void setCellValue(Cell cell, Object value) {
//        if (value == null) {
//            cell.setCellValue("");
//        } else if (value instanceof Number) {
//            cell.setCellValue(((Number) value).doubleValue());
//        } else if (value instanceof Boolean) {
//            cell.setCellValue((Boolean) value);
//        } else {
//            cell.setCellValue(value.toString());
//        }
//    }
//}

package com.maxrayyy.reportservice.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * @Description: 用于导出Excel
 */
@Slf4j
public class ExcelUtil {

    /**
     * 用于导出Excel表
     *
     * @param fileName    导出excel文件名
     * @param exportClass 导出List中的对象.class
     * @param data        需要导出的List
     * @return ResponseEntity <byte[]> 对象
     */
    public static ResponseEntity<byte[]> generateExcel(List data, Class exportClass, String fileName) {

        //对于中文名称的文件进行转码
        try {
            fileName = URLEncoder.encode(fileName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error("文件名编码转换失败 ", e);
            return writeToTxt("文件名编码转换失败", "error");
        }


        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            EasyExcel.write(byteArrayOutputStream, exportClass)
                    .autoCloseStream(Boolean.TRUE).sheet("sheet1")
                    .doWrite(data);
        } catch (Exception e) {
            log.error("导出excel转换错误", e);
            return writeToTxt("导出excel转换错误", "error");
        }
        byte[] bytes = byteArrayOutputStream.toByteArray();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
        HttpStatus statusCode = HttpStatus.OK;
        ResponseEntity<byte[]> entity = new ResponseEntity<>(bytes, headers, statusCode);
        return entity;
    }

    /**
     * 用于导出txt
     *
     * @param message 此字符串信息将会被写入到txt文件中
     * @return 响应实体
     */
    public static ResponseEntity<byte[]> writeToTxt(String message, String fileName) {

        //对于中文名称的文件进行转码
        try {
            fileName = URLEncoder.encode(fileName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error("文件名编码转换失败 ", e);
            return writeToTxt("文件名编码转换失败", "error");
        }


        ByteArrayOutputStream baos = new ByteArrayOutputStream();


        BufferedOutputStream bufferedOutputStream = null;
        StringBuffer write;
        try {
            bufferedOutputStream = new BufferedOutputStream(baos);

            write = new StringBuffer();
            write.append(message);
            bufferedOutputStream.write(write.toString().getBytes("UTF-8"));

            bufferedOutputStream.flush();
            bufferedOutputStream.close();
        } catch (Exception e) {
            log.error("导出错误 ", e);
            return writeToTxt("导出错误", "error");
        } finally {
            try {
                bufferedOutputStream.close();
            } catch (Exception e) {
                log.error("关闭字节流错误 ", e);
            }
        }

        byte[] bytes = baos.toByteArray();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment;filename=" + fileName + ".txt");
        HttpStatus statusCode = HttpStatus.OK;
        ResponseEntity<byte[]> entity = new ResponseEntity<>(bytes, headers, statusCode);

        return entity;

    }

    /**
     * 用于读取excel
     *
     * @param file                  文件参数
     * @param importClass           导入对象class
     * @param analysisEventListener 分析事件监听器
     */
    public static void readExcel(MultipartFile file, Class importClass, AnalysisEventListener analysisEventListener) {
        // 有个很重要的点 DemoDataListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
        try {
            EasyExcel.read(file.getInputStream(), importClass, analysisEventListener).sheet().doRead();
        } catch (IOException e) {
            log.error("excel文件上传出现错误: {}", e.getMessage());
        } catch (Exception e) {
            log.error("出现未知错误: {}", e.getMessage());
        }
    }

}