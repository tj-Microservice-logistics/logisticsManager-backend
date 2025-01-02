package com.maxrayyy.commonmodule.dto.transportDto;

import lombok.Data;

import org.springframework.http.HttpStatus;

@Data
// 通用响应类，结合实体 Dto 类获取返回信息
public class ResponseMessage<T> {

    private Integer code;
    private String message;
    private T data;

    public ResponseMessage() {}

    public ResponseMessage(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> ResponseMessage<T> success(T data) {
        return new ResponseMessage<>(HttpStatus.OK.value(), "success!", data);
    }

    public static <T> ResponseMessage<T> failure(String message) {
        return new ResponseMessage<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), message, null);
    }

}
