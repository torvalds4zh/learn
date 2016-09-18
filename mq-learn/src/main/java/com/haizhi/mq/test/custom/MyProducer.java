package com.haizhi.mq.test.custom;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by chenbo on 15/11/11.
 */
public class MyProducer{
    public final static String TOPIC = "mqTest";
    private final static String key = "mq key";

    private KafkaProducer<String,String> producer;
    private final static String resources = "/kafka-producer.properties";

    public MyProducer(){
        InputStream inputStream = this.getClass().getResourceAsStream(resources);
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        producer = new KafkaProducer<String,String>(properties);
    }

    public void send(){
        String message = "message content";
        ProducerRecord<String,String> data = new ProducerRecord<String, String>(TOPIC,key,message);
        producer.send(data);
    }

    public void close(){
        producer.close();
    }
}
