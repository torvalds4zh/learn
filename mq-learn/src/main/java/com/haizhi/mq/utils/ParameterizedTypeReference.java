package com.haizhi.mq.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


/**
 *
 *  参数化类型引用
 *  Created by xiaolezheng on 15/8/27.
 */
public abstract class ParameterizedTypeReference<T> {

    private final Type type;

    public ParameterizedTypeReference() {
        Type clazz = getClass().getGenericSuperclass();
        type = ((ParameterizedType) clazz).getActualTypeArguments()[0];
    }

    public Type getType() {
        return this.type;
    }
}