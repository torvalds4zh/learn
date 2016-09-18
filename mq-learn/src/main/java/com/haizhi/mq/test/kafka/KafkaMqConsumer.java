package com.haizhi.mq.test.kafka;


import com.google.common.collect.Maps;
import com.haizhi.mq.exceptions.MsgException;
import com.haizhi.mq.test.MqMessage;
import com.haizhi.mq.test.MsgConsumer;
import com.haizhi.mq.test.MsgConvert;
import com.haizhi.mq.utils.PropertyUtil;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * kafka 消费者, 目前仅支持一个应用消费者配置信息一致,在项目资源文件夹增加资源文件kafka-consumer.properties,内容可以参考模板文件 kafka-consumer.properties;
 * 使用方实现方法 onMessage即可;
 *
 * Created by xiaolezheng on 15/9/1.
 */
public abstract class KafkaMqConsumer implements MsgConsumer {
    protected static final Logger logger = LoggerFactory.getLogger(KafkaMqConsumer.class);
    private static final String DEFAULT_CONFIG_FILE = "kafka-consumer";
    private static final String consumerConfigFile = PropertyUtil.getProperty("kafka.consumer.config", DEFAULT_CONFIG_FILE);
    private static final Properties PROPS = PropertyUtil.loadProperties(consumerConfigFile);
    private static final ConsumerConfig consumerConfig = new ConsumerConfig(PROPS);
    private ConsumerConnector consumer = kafka.consumer.Consumer.createJavaConsumerConnector(consumerConfig);


    /**
     * 监听的消息主题
     */
    private String topic;

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public KafkaMqConsumer(final String topic){
        this.topic = topic;

        logger.info(String.format("Kafka Consumer start, listen topic: %s, config: %s", topic, PROPS));

        Runtime.getRuntime().addShutdownHook(new Thread(){
            public void run(){
                logger.info(String.format("Kafka Consumer stop, listen topic: %s, config: %s", topic, PROPS));
                consumer.shutdown();
            }
        });
    }

    public void onListen() {

        if(topic == null || "".equals(topic)){
            throw new MsgException("Please set the topic for listening.");
        }

        try {
            Map<String, Integer> topicCountMap = Maps.newHashMapWithExpectedSize(1);
            topicCountMap.put(topic, 1);
            Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);
            KafkaStream<byte[], byte[]> stream = consumerMap.get(topic).get(0);
            for (MessageAndMetadata<byte[], byte[]> messageAndMetadata : stream) {
                MqMessage mqMessage = MsgConvert.convert(messageAndMetadata);

                // 实际业务接口
                onMessage(mqMessage);
            }
        }catch (Exception e){
            // TODO 需要监控失败
            logger.error("消费消息失败,topic: {}", topic, e);
        }
    }
}
