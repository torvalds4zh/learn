package com.haizhi.mq.test.rabbit.learn;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by chenbo on 15/11/19.
 */
public class EmitLogTopic {
    public static final String EXCHANGE_NAME = "topic_test";

    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        Connection connection = null;
        Channel channel = null;
        try {
            connection = factory.newConnection();
            channel = connection.createChannel();

            channel.queueDeclare("topic_queue",true,false,false,null);
            channel.exchangeDeclare(EXCHANGE_NAME,"topic");

            String routeKey = getRouting(args);
            String message = getMessage(args);

            channel.basicPublish(EXCHANGE_NAME,routeKey,null,message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + routeKey + "':'" + message + "'");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } finally {
            if(connection != null){
                try {
                    connection.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String getRouting(String[] strings){
        if(strings.length < 1){
            return "anonymous.info";
        }
        return strings[0];
    }

    private static String getMessage(String[] strings){
        if(strings.length < 2){
            return "Hello World!";
        }
        return joinString(strings," ",1);
    }

    private static String joinString(String[] strings, String delimiter, int startIndex) {
        int length = strings.length;
        if (length == 0 ) return "";
        if (length < startIndex ) return "";
        StringBuilder words = new StringBuilder(strings[startIndex]);
        for (int i = startIndex + 1; i < length; i++) {
            words.append(delimiter).append(strings[i]);
        }
        return words.toString();
    }
}
