package com.haizhi.mq.test.rabbit.learn;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by chenbo on 15/11/19.
 */
public class ReceiveLogsTopic {
    public static final String EXCHANGE_NAME = "topic_test";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");

//        String queueName = channel.queueDeclare().getQueue();
        String queueName = channel.queueDeclare("topic_queue", true, false, false, null).getQueue();

        if (args.length < 1) {
            System.err.println("Usage: ReceiveLogsTopic [binding_key]...");
            System.exit(1);
        }

        for (String bindingKey : args) {
            channel.queueBind(queueName, EXCHANGE_NAME, bindingKey);
        }

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C queueName: " + queueName);
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + envelope.getRoutingKey() + "':'" + message + "'");
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };

        channel.basicConsume(queueName, consumer);
    }
}
