package com.haizhi.mq.test.kafka;

import com.google.common.collect.Lists;
import com.haizhi.mq.base.Callback;
import com.haizhi.mq.test.MqMessage;
import com.haizhi.mq.test.MsgConvert;
import com.haizhi.mq.test.MsgProducer;
import com.haizhi.mq.utils.PropertyUtil;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.Metric;
import org.apache.kafka.common.MetricName;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.serialization.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Future;

/**
 * kafka 消息生产者, 单例; 使用方法项目资源文件夹增加资源文件 kafka-producer.properties 配置producer信息,内容参考kafka-producer.cfg
 *
 * Created by xiaolezheng on 15/9/1.
 */
//public final class KafkaMqProducer implements MsgProducer{
public final class KafkaMqProducer implements MsgProducer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaMqProducer.class);

    private static final String DEFAULT_CONFIG_FILE = "kafka-producer";
    private static final String PRODUCER_CONFIG_PROPERTIES = PropertyUtil.getProperty("kafka.producer.config", DEFAULT_CONFIG_FILE);

    private static final Properties prop = PropertyUtil.loadProperties(PRODUCER_CONFIG_PROPERTIES);
    private static final List<Callback> callbackList = Lists.newArrayList();

    private KafkaProducer<String, Object> producer;

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


    public void send(final MqMessage mqMessage) {
        try{
            ProducerRecord<String,Object> producerRecord = MsgConvert.convertKafkaMsg(mqMessage);

            producer.send(producerRecord, new org.apache.kafka.clients.producer.Callback() {
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    if(exception == null){
                        processOnSuccess(mqMessage);
                    } else {
                        processOnException(mqMessage, exception);
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

    public Future<RecordMetadata> send(ProducerRecord<String, Object> record) {
        return producer.send(record);
    }

    public Future<RecordMetadata> send(ProducerRecord<String, Object> record, org.apache.kafka.clients.producer.Callback callback) {
        return producer.send(record,callback);
    }

    public List<PartitionInfo> partitionsFor(String topic) {
        return producer.partitionsFor(topic);
    }

    public Map<MetricName, ? extends Metric> metrics() {
        return producer.metrics();
    }

    public void close() {
        producer.close();
    }
}
