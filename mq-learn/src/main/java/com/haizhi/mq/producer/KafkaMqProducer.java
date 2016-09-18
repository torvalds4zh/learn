package com.haizhi.mq.producer;

import com.google.common.collect.Lists;
import com.haizhi.mq.base.Callback;
import com.haizhi.mq.message.KafkaMessage;
import com.haizhi.mq.message.MessageConvert;
import com.haizhi.mq.message.MqMessage;
import com.haizhi.mq.utils.PropertyUtil;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Properties;

/**
 * Created by chenbo on 16/3/30.
 */
public class KafkaMqProducer implements MessageProducer {
    private static final Logger logger = LoggerFactory.getLogger(KafkaMqProducer.class);

    private static final String DEFAULT_CONFIG_FILE = "kafka-producer";
    private static final String PRODUCER_CONFIG_PROPERTIES = PropertyUtil.getProperty("kafka.producer.config", DEFAULT_CONFIG_FILE);

    private static final Properties prop = PropertyUtil.loadProperties(PRODUCER_CONFIG_PROPERTIES);
    private static final List<Callback> callbackList = Lists.newArrayList();

    private org.apache.kafka.clients.producer.KafkaProducer<String, Object> producer;

//    static {
//        logger.info(String.format("Kafka Producer start, config: %s", properties));
//
//        //VM退出会调用producer的关闭方法
//        Runtime.getRuntime().addShutdownHook(new Thread(){
//            public void run(){
//                logger.info("Kafka Producer stop.......................");
//                producer.close();
//            }
//        });
//    }

    /**
     * 默认构造方法，加载默认配置
     */
    public KafkaMqProducer(){
        producer = new KafkaProducer(prop);
    }

    /**
     * 加载producer手动配置文件
     * @param properties
     */
    public KafkaMqProducer(Properties properties){
        producer = new KafkaProducer(properties);
    }

    public KafkaMqProducer(Properties properties, Serializer<String> keySerializer, Serializer<Object> valueSerializer) {
        producer = new KafkaProducer(properties,keySerializer,valueSerializer);
    }


    public void send(MqMessage mqMessage) {
        final KafkaMessage message = (KafkaMessage) mqMessage;
        try{
            ProducerRecord<String,Object> producerRecord = MessageConvert.convertKafkaMsg(message);

            producer.send(producerRecord, new org.apache.kafka.clients.producer.Callback() {
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    if(exception == null){
                        processOnSuccess(message);
                    } else {
                        processOnException(message, exception);
                    }
                }
            });
        } catch (Exception e){
            // TODO 需要监控
            processOnException(mqMessage, e);
            logger.error("消息发送失败, mqMessage: {}", mqMessage, e);
        }
    }

    public void addCallback(Callback callback) {
        callbackList.add(callback);
    }

    private void processOnSuccess(MqMessage mqMessage){
        for(Callback callback: callbackList){
            callback.onSuccess(mqMessage);
        }
    }

    private void processOnException(MqMessage mqMessage, Throwable e){
        for(Callback callback: callbackList){
            callback.onException(mqMessage, e);
        }
    }

    public void close() {
        producer.close();
    }
}
