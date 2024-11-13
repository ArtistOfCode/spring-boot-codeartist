package com.codeartist.component.core.util;

import com.codeartist.component.core.support.serializer.JacksonSerializer;
import com.codeartist.component.core.support.serializer.TypeRef;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;

/**
 * JSON工具类
 *
 * @author AiJiangnan
 * @date 2020/9/8
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JSON {

    private static final ObjectMapper objectMapper = JacksonSerializer.simpleMapper();

    public static String toJSONString(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw exception(e);
        }
    }

    public static String toJSONStringPretty(Object value) {
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw exception(e);
        }
    }

    public static <T> T parseObject(String value, Class<T> valueType) {
        try {
            return objectMapper.readValue(value, valueType);
        } catch (IOException e) {
            throw exception(e);
        }
    }

    public static <T> T parseObject(byte[] src, Class<T> valueType) {
        try {
            return objectMapper.readValue(src, valueType);
        } catch (IOException e) {
            throw exception(e);
        }
    }

    public static <T> T parseObject(String value, TypeRef<T> valueTypeRef) {
        try {
            return objectMapper.readValue(value, valueTypeRef);
        } catch (IOException e) {
            throw exception(e);
        }
    }

    public static <T> T parseObject(byte[] src, TypeRef<T> valueTypeRef) {
        try {
            return objectMapper.readValue(src, valueTypeRef);
        } catch (IOException e) {
            throw exception(e);
        }
    }

    public static JsonNode parseNode(String value) {
        try {
            return objectMapper.readTree(value);
        } catch (IOException e) {
            throw exception(e);
        }
    }

    public static JsonNode parseNode(byte[] src) {
        try {
            return objectMapper.readTree(src);
        } catch (IOException e) {
            throw exception(e);
        }
    }

    private static RuntimeException exception(Exception e) {
        return new RuntimeException("JSON parse error.", e);
    }
}
