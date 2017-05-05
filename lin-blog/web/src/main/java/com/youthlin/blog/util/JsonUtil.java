package com.youthlin.blog.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Json 转换工具类.
 * <p>
 * 创建者： youthlin.chen 日期： 2017-04-06 23:03.
 */
public final class JsonUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);//没有字段的类：如内部Mapper
    }

    private JsonUtil() {
    }

    /**
     * Java 对象转 JSON.
     *
     * @param object 要转换的 Java 对象
     * @return 转换后的 json 字符串，当异常时为 {@code null}
     */
    public static String toJson(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            LOGGER.error("Object to JSON error. object = {}", object, e);
        }
        return null;
    }

    /**
     * 从 json 转换为相应 Java 对象.
     *
     * @param json  JSON 字符串
     * @param clazz 对应的 Java 类
     * @return JSON 字符串对应的 Java 类实例。当 json 为 null 时，返回 null, 当发生 IO 异常时返回 null
     * @throws NullPointerException 当 clazz 为 null 时
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        Preconditions.checkNotNull(clazz);
        if (json == null) {
            LOGGER.warn("Json = null, clazz = {}", clazz);
            return null;
        }
        try {
            return mapper.readValue(json, clazz);
        } catch (IOException e) {
            LOGGER.error("Json to Object error. json = {}, class type = {}", json, clazz, e);
        }
        return null;
    }

}
