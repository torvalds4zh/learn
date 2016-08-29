package com.haizhi.mq.test;

/**
 * 消息消费者接口
 *
 * Created by xiaolezheng on 15/8/27.
 */
public interface MsgConsumer {

    void onListen();

    void onMessage(MqMessage mqMessage);
}
