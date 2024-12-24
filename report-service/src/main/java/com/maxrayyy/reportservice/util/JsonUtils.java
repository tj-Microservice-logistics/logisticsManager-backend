package com.maxrayyy.reportservice.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class JsonUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("Convert object to JSON failed", e);
            throw new RuntimeException("Convert to JSON failed", e);
        }
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            log.error("Parse JSON to object failed", e);
            throw new RuntimeException("Parse JSON failed", e);
        }
    }

    public static JsonNode parseJson(String json) {
        try {
            return objectMapper.readTree(json);
        } catch (JsonProcessingException e) {
            log.error("Parse JSON to JsonNode failed", e);
            throw new RuntimeException("Parse JSON failed", e);
        }
    }
    
    public static <T> List<T> parseList(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, new TypeReference<List<T>>() {});
        } catch (JsonProcessingException e) {
            log.error("Parse JSON to List failed", e);
            throw new RuntimeException("Parse JSON to List failed", e);
        }
    }
    
    public static Map<String, Object> parseMap(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<Map<String, Object>>() {});
        } catch (JsonProcessingException e) {
            log.error("Parse JSON to Map failed", e);
            throw new RuntimeException("Parse JSON to Map failed", e);
        }
    }
} 