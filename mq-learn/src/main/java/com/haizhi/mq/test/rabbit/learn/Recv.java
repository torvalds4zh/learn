package com.haizhi.mq.test.rabbit.learn;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by chenbo on 15/11/16.
 */
public class Recv {
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args){

        //创建连接
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            //创建queue
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            //创建消费者
            Consumer consumer = new DefaultConsumer(channel){
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    //获取消息内容
                    String message = new String(body,"UTF-8");
                    System.out.println(" [x] Received '" + message + "'");
                }
            };

            //启动消费
            channel.basicConsume(QUEUE_NAME,true,consumer);

            channel.close();
            connection.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

    }
}
