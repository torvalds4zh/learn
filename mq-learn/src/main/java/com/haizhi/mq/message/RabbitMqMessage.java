package com.haizhi.mq.message;

import com.haizhi.mq.base.PrintEnable;

import java.io.Serializable;

/**
 * Created by chenbo on 16/3/30.
 */
public class RabbitMqMessage extends PrintEnable implements MqMessage, Serializable {

    //相当于producer与queen之间的中间者
    private String exchange;

    private String queueName;

    //路由规则
    private String routingKey;

    //消息体
    private MessageBody body;

    //指定queueName,不指定exchange -- fanout 消费端queueDeclare就行
    //指定queueName,指定exchange,指定routingKey，那么消费端就要binding

    public RabbitMqMessage(){
        this("default_queen");
    }

    /**
     * 用于最简单的生产者-消费者模型
     * @param queueName
     */
    public RabbitMqMessage(String queueName){
        this("",queueName,null,null);
    }

    /**
     * 用于发布-订阅模型
     * @param exchange
     * @param queueName
     * @param routingKey 路由规则
     */
    public RabbitMqMessage(String exchange, String queueName,String routingKey){
        this(exchange,queueName,routingKey,null);
    }

    public RabbitMqMessage(String exchange, String queueName,String routingKey, MessageBody body) {
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

    public MessageBody getBody() {
        return body;
    }

    public void setBody(MessageBody body) {
        this.body = body;
    }

}
