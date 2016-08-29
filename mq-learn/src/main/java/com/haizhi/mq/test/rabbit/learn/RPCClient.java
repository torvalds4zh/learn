package com.haizhi.mq.test.rabbit.learn;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by chenbo on 15/11/19.
 */
public class RPCClient {
    public static final String RQUEST_QUEUE_NAME = "rpc_queue";

    private Channel channel;
    private Connection connection;
    private String replyQueueName;
    private QueueingConsumer consumer;

    public RPCClient() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        connection = factory.newConnection();
        channel = connection.createChannel();

        replyQueueName = channel.queueDeclare().getQueue();
        consumer = new QueueingConsumer(channel);
        channel.basicConsume(replyQueueName, true, consumer);
    }

    public String call(String message) throws IOException, InterruptedException {
        String response = null;
        String corrId = UUID.randomUUID().toString();

        AMQP.BasicProperties properties = new AMQP.BasicProperties
                .Builder().correlationId(corrId).replyTo(replyQueueName).build();
        channel.basicPublish("",RQUEST_QUEUE_NAME,properties,message.getBytes());

        while(true){
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            if(delivery.getProperties().getCorrelationId().equals(corrId)){
                response = new String(delivery.getBody(),"UTF-8");
                break;
            }
        }
        return response;
    }

    public void close() throws IOException {
        connection.close();
    }

    public static void main(String[] args) {
        RPCClient rpc = null;
        String response = null;
        try {
            rpc = new RPCClient();

            System.out.println(" [x] Requesting fib(30)");
            response = rpc.call("30");
            System.out.println(" [.] Got '" + response + "'");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                rpc.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
