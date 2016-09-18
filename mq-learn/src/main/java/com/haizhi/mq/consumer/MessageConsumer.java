package com.haizhi.mq.consumer;

import com.haizhi.mq.message.MqMessage;

/**
 * Created by chenbo on 16/3/30.
 */
public interface MessageConsumer {
    void listen();

    void consume(MqMessage mqMessage);
}
