package com.haizhi.mq.test.rabbit.learn;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by chenbo on 15/11/16.
 */
public class Sent {
    private static String QUEUE_NAME = "hello";

    public static void main(String[] args){
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try {
            //创建连接
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            //创建queue
            channel.queueDeclare(QUEUE_NAME,false,false,false,null);
            String message = "Hello World!";
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");

            channel.close();
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
