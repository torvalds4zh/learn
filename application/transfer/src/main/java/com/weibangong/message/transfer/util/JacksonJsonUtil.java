package com.weibangong.message.transfer.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Created by HaiZhi on 28/6/16.
 */
public class JacksonJsonUtil {

    private static ObjectMapper objectMapper;

    static {
        JsonFactory jsonFactory = new JsonFactory();
        jsonFactory.enable(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS);

        objectMapper = new ObjectMapper(jsonFactory);
        objectMapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
        objectMapper.enable(SerializationFeature.WRITE_ENUMS_USING_INDEX);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
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

}
