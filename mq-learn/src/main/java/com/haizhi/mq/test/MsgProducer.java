package com.haizhi.mq.test;


import com.haizhi.mq.base.Callback;

/**
 * 消息生产者接口
 * 
 * Created by xiaolezheng on 15/8/27.
 */
public interface MsgProducer {
    /**
     * 发送消息接口
     *
     * @param mqMessage 消息对象, 业务方做好校验
     */
    void send(MqMessage mqMessage);

    /**
     * 发送消息添加回调
     *
     * @param callback
     */
    void addCallback(Callback callback);

}
