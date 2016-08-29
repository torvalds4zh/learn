package com.haizhi.mq;

import com.haizhi.mq.test.kafka.KafkaMqConsumer;
import com.haizhi.mq.test.MqMessage;
import com.haizhi.mq.test.MsgConsumer;

/**
 * Created by chenbo on 15/11/11.
 */
public class KafkaMqConsumerTest {

    public static void main(String[] args){
        String topic = "mqTest";

        MsgConsumer consumer = new KafkaMqConsumer(topic) {
            public void onMessage(MqMessage mqMessage) {
                System.out.println("mqMessage = [" + mqMessage + "]");
            }
        };

        consumer.onListen();
    }

}
