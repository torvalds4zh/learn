package com.haizhi.mq.consumer;

import com.google.common.collect.Maps;
import com.haizhi.mq.exceptions.MsgException;
import com.haizhi.mq.message.KafkaMessage;
import com.haizhi.mq.message.MessageConvert;
import com.haizhi.mq.message.MqMessage;
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
 * Created by chenbo on 16/3/30.
 */
public abstract class KafkaMqConsumer implements MessageConsumer{
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

    public void listen() {

        if(topic == null || "".equals(topic)){
            throw new MsgException("Please set the topic for listening.");
        }

        try {
            Map<String, Integer> topicCountMap = Maps.newHashMapWithExpectedSize(1);
            topicCountMap.put(topic, 1);
            Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);
            KafkaStream<byte[], byte[]> stream = consumerMap.get(topic).get(0);
            for (MessageAndMetadata<byte[], byte[]> messageAndMetadata : stream) {
                KafkaMessage mqMessage = MessageConvert.convertToKafkaMessage(messageAndMetadata);

                // 实际业务接口
                consume(mqMessage);
            }
        }catch (Exception e){
            // TODO 需要监控失败
            logger.error("消费消息失败,topic: {}", topic, e);
        }
    }

}
