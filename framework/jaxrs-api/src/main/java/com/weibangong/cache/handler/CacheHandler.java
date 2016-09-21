package com.weibangong.cache.handler;

import com.weibangong.cache.CacheContext;

/**
 * 从cache中获取结果后的处理
 *
 * Created by zhangping on 16/6/4.
 */
public interface CacheHandler<T> {

    T handler (CacheContext cacheContext);

}
