package com.haizhi.mq.test.rabbit;

import java.util.Arrays;

/**
 * Created by chenbo on 15/11/20.
 */
public class RabbitMessage {
    private String exchange;
    private String queueName;
    private String routingKey;
    private byte[] body;

    //指定queueName,不指定exchange -- fanout 消费端queueDeclare就行
    //指定queueName,指定exchange,指定routingKey，那么消费端就要binding
    public RabbitMessage(String queueName){
        this("",null,null,null);
    }

    public RabbitMessage(String exchange, String queueName,String routingKey){
        this(exchange,queueName,routingKey,null);
    }

    public RabbitMessage(String exchange, String queueName,String routingKey, byte[] body) {
        this.exchange = exchange;
        this.queueName = queueName;
        this.routingKey = routingKey;
        this.body = body;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "RabbitMessage{" +
                "exchange='" + exchange + '\'' +
                ", queueName='" + queueName + '\'' +
                ", routingKey='" + routingKey + '\'' +
                ", body=" + Arrays.toString(body) +
                '}';
    }
}
