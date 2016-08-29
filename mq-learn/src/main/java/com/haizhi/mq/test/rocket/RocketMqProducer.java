package com.haizhi.mq.test.rocket;

import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.MessageQueueSelector;
import com.alibaba.rocketmq.client.producer.SendCallback;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.MessageQueue;
import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;
import com.haizhi.mq.base.Callback;
import com.haizhi.mq.base.Preconditions;
import com.haizhi.mq.exceptions.MsgException;
import com.haizhi.mq.test.MqMessage;
import com.haizhi.mq.test.MsgConvert;
import com.haizhi.mq.test.MsgProducer;
import com.haizhi.mq.utils.PropertyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * RocketMq 生产者, 全局单例; 可以设置发送消息回调；
 * <p/>
 * Created by xiaolezheng on 15/8/27.
 */
public final class RocketMqProducer implements MsgProducer {
    private static final Logger logger = LoggerFactory.getLogger(RocketMqProducer.class);
    private static final String DEFAULT_PRODUCER_GROUP = PropertyUtil.getProperty("mq.producerGroup", "Producer");
    private static final String DEFAULT_NAMESRV_ADDR = PropertyUtil.getProperty("mq.namesrv.addr", "123.126.105.45:9876");
    private static final int DEFAULT_TIMEOUT = Ints.tryParse(PropertyUtil.getProperty("mq.producer.send.timeout", "500"));
    private static final DefaultMQProducer producer = new DefaultMQProducer(DEFAULT_PRODUCER_GROUP);

    private static final MsgProducer instance = new RocketMqProducer();

    private static final List<Callback> callbackList = Lists.newArrayList();

    static {
        try {
            producer.setNamesrvAddr(DEFAULT_NAMESRV_ADDR);
            producer.setSendMsgTimeout(DEFAULT_TIMEOUT);


            producer.start();

            logger.info(String.format("RocketMQ Producer start, group: %s, namesrv: %s", DEFAULT_PRODUCER_GROUP, DEFAULT_NAMESRV_ADDR));

            /**
             * 添加jvm关闭钩子
             */
            Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                    producer.shutdown();
                }
            });
        } catch (Exception e) {
            logger.error("RocketMqProducer 启动错误", e);
        }
    }

    private RocketMqProducer() {
    }

    public static MsgProducer getInstance() {
        return instance;
    }

    public void send(final MqMessage mqMessage) {
        Preconditions.checkNotNull(mqMessage, "消息不能为空");

        try {
            com.alibaba.rocketmq.common.message.Message msg = MsgConvert.convertRocketMsg(mqMessage);

            producer.send(msg, new MessageQueueSelector() {
                public MessageQueue select(List<MessageQueue> mqs, com.alibaba.rocketmq.common.message.Message msg, Object arg) {
                    MqMessage mqMessage = (MqMessage) arg;
                    // key相同的入到同一个队列保证顺序
                    int index = mqMessage.getKey().hashCode() % mqs.size();
                    return mqs.get(index);
                }
            }, mqMessage, new SendCallback() {
                public void onSuccess(SendResult sendResult) {
                    for (Callback callback : callbackList) {
                        callback.onSuccess(mqMessage);
                    }
                }

                public void onException(Throwable e) {
                    for (Callback callback : callbackList) {
                        callback.onException(mqMessage, e);
                    }
                }
            }, DEFAULT_TIMEOUT);

        } catch (Exception e) {
            //TODO 需要监控失败
            throw new MsgException(e, "消息发送异常, mqMessage: %s", mqMessage);
        }

    }

    public void addCallback(Callback callback) {
        callbackList.add(callback);
    }
}
