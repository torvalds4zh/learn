package com.weibangong.cache;

import com.weibangong.cache.handler.CacheHandler;

import java.util.Map;

/**
 * cache 对外提供服务
 * Created by zhangping on 16/6/4.
 */
public interface CacheService {

    <T> T get (String key, CacheHandler<T> handler);

    <T> T get (String key, CacheHandler<T> handler, String... fields);

    /**
     * string key set
     * @param key
     * @param object
     */
    void set (String key, Object object, Integer expireTime);

    void set (String key, Map<String, Object> map, Integer expireTime);
}
