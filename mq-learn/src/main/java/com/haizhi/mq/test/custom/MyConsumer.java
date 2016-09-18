package com.haizhi.mq.test.custom;

import com.haizhi.mq.test.MsgConstant;
import com.haizhi.mq.utils.JsonUtils;
import com.haizhi.mq.utils.ParameterizedTypeReference;
import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.ExecutorService;

/**
 * Created by chenbo on 15/11/11.
 */
public class MyConsumer {
    private ConsumerConnector consumer;
    private final String topic = "mqTest";
    private final String resource = "/kafka-consumer.properties";

    private ExecutorService executor;

    public MyConsumer(){
        InputStream inputStream = this.getClass().getResourceAsStream(resource);
        Properties prop = new Properties();
        try {
            prop.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        consumer = Consumer.createJavaConsumerConnector(new ConsumerConfig(prop));
    }

    public void consume(){
        Map<String,Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put(MyProducer.TOPIC, new Integer(1));

        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);
        KafkaStream<byte[], byte[]> stream = consumerMap.get(MyProducer.TOPIC).get(0);
//        ConsumerIterator<byte[],byte[]> it = stream.iterator();
//        while(it.hasNext()){
//            System.out.println(new String(it.next().message()));
//        }

        for(MessageAndMetadata<byte[],byte[]> messageAndMetadata : stream){
            String key = new String(messageAndMetadata.key(), MsgConstant.UTF_8);
            String message = new String(messageAndMetadata.message(), MsgConstant.UTF_8);
            Map content = JsonUtils.decode(message, new ParameterizedTypeReference<Map<String, String>>() {
                @Override
                public Type getType() {
                    return super.getType();
                }
            });
            System.out.println("key : " + key + " ; message : " + message + " ; content : " + content);
        }
    }
}
