package com.weibangong.cache;

import lombok.Data;

/**
 * Created by zhangping on 16/6/4.
 */
@Data
public class CacheContext {

    private String key;

    /**
     * cache中获取的值
     */
    private Object data;

    /**
     * cache 设置的值
     */
    private Object value;

    public CacheContext (String key, Object data) {
        this.key = key;
        this.data = data;
    }
}
