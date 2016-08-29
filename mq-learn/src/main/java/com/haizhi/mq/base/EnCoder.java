package com.haizhi.mq.base;

/**
 *
 * 自定义序列化，反序列编码接口
 *
 * Created by xiaolezheng on 15/8/30.
 */
public interface EnCoder<T> {
    byte[] encode(T value);

    <T> T encode(byte[] date);
}
