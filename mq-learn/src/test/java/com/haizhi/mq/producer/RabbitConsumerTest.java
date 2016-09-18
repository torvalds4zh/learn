package com.haizhi.mq.producer;

import com.haizhi.mq.message.BDPSyncMessageBody;
import com.haizhi.mq.message.FormTriggerMessageBody;
import com.haizhi.mq.message.MessageConvert;
import com.haizhi.mq.message.ViewMessageBody;
import com.haizhi.mq.test.rabbit.RabbitConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by chenbo on 15/11/21.
 */
public class RabbitConsumerTest {
    private static final Logger logger = LoggerFactory.getLogger(RabbitConsumerTest.class);


    public static void main(String[] args) {

        RabbitConsumer fanoutConsumer = new RabbitConsumer("fanout_exchange","fanout_queue",new String[]{"a"}) {
            @Override
            public void onConsume(byte[] body) {
                BDPSyncMessageBody bdpSyncMessageBody = (BDPSyncMessageBody) MessageConvert.byteToObject(body);
                logger.info("fanout message received: " + bdpSyncMessageBody);
            }
        };

        RabbitConsumer fanoutConsumer2 = new RabbitConsumer("fanout_exchange","fanout_queue",new String[]{"b"}) {
            @Override
            public void onConsume(byte[] body) {
                BDPSyncMessageBody bdpSyncMessageBody = (BDPSyncMessageBody) MessageConvert.byteToObject(body);
                logger.info("fanout2 message received: " + bdpSyncMessageBody);
            }
        };

        RabbitConsumer directConsumer = new RabbitConsumer("direct_exchange","direct_queue",new String[]{"direct"}) {
            @Override
            public void onConsume(byte[] body) {
                FormTriggerMessageBody bdpSyncMessageBody = (FormTriggerMessageBody) MessageConvert.byteToObject(body);
                logger.info("direct message received: " + bdpSyncMessageBody);
            }
        };

        RabbitConsumer directConsumer2 = new RabbitConsumer("direct_exchange","direct_queue",new String[]{"direct2"}) {
            @Override
            public void onConsume(byte[] body) {
                FormTriggerMessageBody formTriggerMessageBody = (FormTriggerMessageBody) MessageConvert.byteToObject(body);
                logger.info("direct2 message received: " + formTriggerMessageBody);
            }
        };

        RabbitConsumer topicConsumer = new RabbitConsumer("topic_exchange", "topic_queue", new String[]{"topic.*"}) {
            @Override
            public void onConsume(byte[] body) {
                ViewMessageBody viewMessageBody = (ViewMessageBody) MessageConvert.byteToObject(body);
                logger.info("topic message received: " + viewMessageBody);
            }
        };

        RabbitConsumer topicConsumer2 = new RabbitConsumer("topic_exchange", "topic_queue", new String[]{"*.ack"}) {
            @Override
            public void onConsume(byte[] body) {
                ViewMessageBody viewMessageBody = (ViewMessageBody) MessageConvert.byteToObject(body);
                logger.info("topic2 message received: " + viewMessageBody);
            }
        };

        try {
            fanoutConsumer.consume();
            fanoutConsumer2.consume();
            directConsumer.consume();
            directConsumer2.consume();
            topicConsumer.consume();
            topicConsumer2.consume();

//            fanoutConsumer.closeConnection();
//            fanoutConsumer2.closeConnection();
//            directConsumer.closeConnection();
//            directConsumer2.closeConnection();
//            topicConsumer.closeConnection();
//            topicConsumer2.closeConnection();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

}
