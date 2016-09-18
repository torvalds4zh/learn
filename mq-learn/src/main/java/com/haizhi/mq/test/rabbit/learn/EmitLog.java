package com.haizhi.mq.test.rabbit.learn;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by chenbo on 15/11/18.
 */
public class EmitLog {
    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] argv) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

        String message = getMessage(argv);

        channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
        System.out.println(" [X] Sent '" + message + "'");

        channel.close();
        connection.close();
    }

    private static String getMessage(String[] strings) {
        if (strings.length < 1) {
            return "info: Hello World!";
        }
        return joinStrings(strings, " ");
    }

    private static String joinStrings(String[] strings, String delimiter) {
        int length = strings.length;
        if (length == 0)
            return "";

        StringBuilder sb = new StringBuilder(strings[0]);
        for (int i = 0; i < length; i++) {
            sb.append(delimiter).append(strings[i]);
        }

        return sb.toString();
    }
}
