package com.haizhi.mq.base;


/**
 * 回调接口
 *
 * Created by xiaolezheng on 15/8/28.
 */
public interface Callback<T> {
    void onSuccess(final T data);

    void onException(final T data, final Throwable e);
}
