package com.haizhi.mq.test.rabbit.learn;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by chenbo on 15/11/17.
 */
public class NewTask {
    private static final String TASK_QUEUE_NAME = "task_queue";

    public static void main(String[] argv) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);

            String message = getMessage(argv);

            for (int i = 0; i < message.split(",").length; i++) {
                channel.basicPublish("", TASK_QUEUE_NAME,
                        MessageProperties.PERSISTENT_TEXT_PLAIN,
                        message.split(",")[i].getBytes("UTF-8"));
                System.out.println(" [X] Sent '" + message.split(",")[i] + "'");
            }

            channel.close();
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

    }

    private static String getMessage(String[] strings){
        if(strings.length < 1){
            return "Hello World!";
        }
        return joinStrings(strings, ",");
    }

    private static String joinStrings(String[] strings, String delimiter){
        int length = strings.length;
        if (length == 0) return "";
        StringBuilder words = new StringBuilder(strings[0]);
        for (int i = 1; i < length; i++) {
            words.append(delimiter).append(strings[i]);
        }
        return words.toString();
    }
}
