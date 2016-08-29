package com.weibangong.camel.jms;

import org.apache.camel.*;
import org.apache.camel.impl.DefaultCamelContext;
import org.junit.Test;

/**
 * Created by chenbo on 16/8/29.
 */
public class CamelJmsToMockWithRabbitMq {

    @Test
    public void camelRabbitMq() throws Exception {
        CamelContext context = new DefaultCamelContext();

        context.start();

        Endpoint endPoint = context.getEndpoint("rabbitmq://localhost:5672/tasks?username=guest&password=guest&autoDelete=false&routingKey=camel&queue=task_queue");

        ProducerTemplate producerTemplate = context.createProducerTemplate();

        producerTemplate.setDefaultEndpoint(endPoint);
        producerTemplate.sendBody("=====one=====");
        producerTemplate.sendBody("=====two=====");
        producerTemplate.sendBody("=====three=====");
        producerTemplate.sendBody("=====four=====");
        producerTemplate.sendBody("done");

        ConsumerTemplate consumerTemplate = context.createConsumerTemplate();
        String body = null;
        while (!"done".equals(body)) {
            Exchange receive = consumerTemplate.receive(endPoint);
            body = receive.getIn().getBody(String.class);
            System.out.println("consume " + body);
        }

        Thread.sleep(1000);
        context.stop();
    }
}
