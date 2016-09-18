package com.haizhi.mq.message;

import com.haizhi.mq.base.PrintEnable;

import java.io.Serializable;

/**
 * Created by chenbo on 16/3/30.
 */
public class KafkaMessage<K,V> extends PrintEnable implements MqMessage, Serializable {
    //消息主题
    private String topic;

    //用作队列路由规则，比如wbg业务公司Id, 或者clientId；key/value对应的value
    //kafka用作分区规则; 都是为了保证消息顺序性,具有同样key的消息有先后顺序;
    private K key;

    //消息内容
    private V value;

    //The partition to which the record will be sent (or null if no partition was specified)
    private Integer partition;

    public KafkaMessage(String topic, K key, V value) {
        this(topic, null, key, value);
    }

    public KafkaMessage(String topic, Integer partition, K key, V value) {
        this.topic = topic;
        this.partition = partition;
        this.key = key;
        this.value = value;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public Integer getPartition() {
        return partition;
    }

    public void setPartition(Integer partition) {
        this.partition = partition;
    }

}
