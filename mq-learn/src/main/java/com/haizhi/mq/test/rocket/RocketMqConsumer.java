package com.haizhi.mq.test.rocket;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerOrderly;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.haizhi.mq.test.MqMessage;
import com.haizhi.mq.test.MsgConsumer;
import com.haizhi.mq.test.MsgConvert;
import com.haizhi.mq.utils.PropertyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Rocket 消费者, 每个topic对应一个; 业务直接实现 onMessage接口即可；
 * <p/>
 * Created by xiaolezheng on 15/8/27.
 */
public abstract class RocketMqConsumer implements MsgConsumer {
    protected static final Logger logger = LoggerFactory.getLogger(RocketMqConsumer.class);
    private static final String DEFAULT_CONSUMER_GROUP = PropertyUtil.getProperty("mq.consumerGroup", "Consumer");
    private static final String DEFAULT_NAMESRV_ADDR = PropertyUtil.getProperty("mq.namesrv.addr", "123.126.105.45:9876");

    private DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(DEFAULT_CONSUMER_GROUP);
    private String topic;
    private String tag;

    public RocketMqConsumer(){}

    public RocketMqConsumer(String topic, String tag) {
        this.topic = topic;
        this.tag = tag;

        logger.info(String.format("Consumer start, group: %s, namesrv: %s", DEFAULT_CONSUMER_GROUP, DEFAULT_NAMESRV_ADDR));
        /**
         * 添加钩子
         */
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                consumer.shutdown();
            }
        });
    }

    public void onListen() {
        try {
            //订阅PushTopic下Tag为push的消息
            consumer.setNamesrvAddr(DEFAULT_NAMESRV_ADDR);
            consumer.subscribe(topic, tag);
            //程序第一次启动从消息队列头取数据
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.registerMessageListener(
                    new MessageListenerOrderly() {
                        public ConsumeOrderlyStatus consumeMessage(
                                List<MessageExt> list,
                                ConsumeOrderlyContext context) {
                            context.setAutoCommit(false);

                            com.alibaba.rocketmq.common.message.Message msg = list.get(0);
                            MqMessage mqMessage = MsgConvert.convert(msg);

                            try {
                                // 业务处理接口
                                long start = System.currentTimeMillis();

                                logger.info("receive msg: {}", mqMessage);

                                onMessage(mqMessage);
                                long cost = System.currentTimeMillis() - start;

                                logger.info("消息处理耗时分析,topic:{},tag:{},cost:{}", topic, tag, cost);
                            } catch (Exception e) {
                                // TODO 需要完善监控数据
                                logger.error("消息处理失败, mqMessage: {}", mqMessage, e);
                            }

                            return ConsumeOrderlyStatus.SUCCESS;
                        }
                    }

            );

            consumer.start();
        } catch (Exception e) {
            // TODO 需要监控
            logger.error("消息消费失败, topic: {}, tag: {}", topic, tag, e);
        }
    }
}
