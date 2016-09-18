package com.weibangong.bpm.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.weibangong.bpm.model.RequestParameter;

/**
 * Created by chenbo on 16/9/13.
 */
public class ObjectMapperUtils {
    private static ObjectMapper objectMapper;

    static {
        JsonFactory jsonFactory = new JsonFactory();
//        jsonFactory.enable(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS);

        objectMapper = new ObjectMapper(jsonFactory);
        objectMapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        objectMapper.enable(SerializationFeature.WRITE_ENUMS_USING_INDEX);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    }


    public static <T> T json2obj(final String json, final Class<T> clazz) {
        if (null == json) {
            return null;
        }
        try {
            return objectMapper.readValue(json, clazz);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static String obj2json(final Object obj) {
        if (null == obj) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) {
        String testStr = "{\"action\":\"agora\",\"content\":{\"sessionId\":\"25358074\",\"type\":\"1\",\"afterLast\":15,\"millis\":1473753081859,\"tenantId\":1182,\"userId\":28532}}";
        RequestParameter request = ObjectMapperUtils.json2obj(testStr, RequestParameter.class);
        System.out.println(request);
    }
}
