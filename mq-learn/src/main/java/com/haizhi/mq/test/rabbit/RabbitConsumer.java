package com.haizhi.mq.test.rabbit;

import com.haizhi.mq.base.Callback;
import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * Created by chenbo on 15/11/20.
 */
public abstract class RabbitConsumer {
    public static final String TYPE_TOPIC = "topic";
    public static final String[] FANOUT_ROUTING_KEYS = new String[]{"#"};
    private static final String DEFAULT_HOST = "localhost";
    private static final Logger logger = LoggerFactory.getLogger(RabbitConsumer.class);

    private String host;
    private String exchange;
    private String queueName;
    private String[] bindingKeys;

    private ConnectionFactory factory = new ConnectionFactory();
    private Connection connection;
    private Channel channel;

    private List<Callback> callbackList = new ArrayList<Callback>();

    //默认是fanout路由方式
    public RabbitConsumer() {
        this(DEFAULT_HOST, "", null, new String[]{"#"});
    }

    //bindingKeys = {"*"}时采用direct路由方式;
    //bindingKeys = {"#"}时采用fanout路由方式;
    //其他 如 {"xxx.xxx.xxx","aaa,bbb,ccc"}时采用topic方式
    public RabbitConsumer(String exchange, String queueName, String[] bindingKeys) {
        this(DEFAULT_HOST,exchange,queueName,bindingKeys);
    }

    public RabbitConsumer(String host, String exchange, String queueName, String[] bindingKeys) {
        this.host = host;
        this.exchange = exchange;
        this.queueName = queueName;
        this.bindingKeys = bindingKeys;

        //让每个consumer占用不同的connection，以免关闭本连接影响其他consumer
        factory.setHost(host);
        try {
            connection = factory.newConnection();
            channel = connection.createChannel();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    /**
     * 消费端
     * 把异常抛出是为了让使用者根据具体应用场景去处理
     *
     * @throws IOException
     */
    public void consume() throws IOException, TimeoutException {

        if (queueName == null) {
            queueName = channel.queueDeclare().getQueue();
        } else {
            channel.queueDeclare(queueName, true, false, false, null);
        }
        channel.exchangeDeclare(exchange, TYPE_TOPIC, false, false, null);

        channel.basicQos(1);

        for (String bindingKey : bindingKeys) {
            channel.queueBind(queueName, exchange, bindingKey);
        }

        logger.info("Consumer starts, waiting for messages. queueName: {" + queueName + "}, exchange: {" + exchange + "}, bindingKeys: {" + bindingKeys + "}");
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                //接收到消息后的具体执行动作，需要根据自己的使用场景的需求来具体定义
                try {
                    onConsume(body);
                } finally {
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };

        channel.basicConsume(queueName,false, consumer);
    }

    public abstract void onConsume(byte[] body);

    public void closeConnection() {
        if (connection != null) {
            try {
                logger.info("Consumer quit. Connection: {"+connection+"}");
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
