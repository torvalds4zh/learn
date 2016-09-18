package com.haizhi.mq.producer;

import com.haizhi.mq.base.Callback;
import com.haizhi.mq.message.MqMessage;

/**
 * Created by chenbo on 16/3/30.
 */
public interface MessageProducer {
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
