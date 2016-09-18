package com.haizhi.mq.test.rabbit.learn;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by chenbo on 15/11/18.
 */
public class EmitLogDirect {
    private static final String EXCHANEG_NAME = "direct_logs";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANEG_NAME,"direct");

        String routeKey = getRouteKey(args);
        String message = getMessage(args);

        channel.basicPublish(EXCHANEG_NAME,routeKey,null,message.getBytes("UTF-8"));
        System.out.println(" [x] Sent '" + routeKey + "':'" + message + "'");

        channel.close();
        connection.close();
    }

    private static String getRouteKey(String[] args) {
        if(args.length < 1){
            return "warning";
        }
        return args[0];
    }

    private static String getMessage(String[] args) {
        if(args.length < 2){
            return "Hello World!";
        }
        return joinStrings(args," ",1);
    }

    public static String joinStrings(String[] args,String delimiter, int startIndex) {
        int length = args.length;
        if(length == 0){
            return "";
        }
        if(length < startIndex){
            return "";
        }
        StringBuilder sb = new StringBuilder(args[0]);
        for (int i = 0; i < args.length; i++) {
            sb.append(delimiter).append(args[i]);
        }
        return sb.toString();
    }

}
