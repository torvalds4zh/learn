package com.haizhi.mq.producer;

import com.haizhi.mq.message.BDPSyncMessageBody;
import com.haizhi.mq.message.FormTriggerMessageBody;
import com.haizhi.mq.message.RabbitMqMessage;
import com.haizhi.mq.message.ViewMessageBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by chenbo on 15/11/20.
 */
public class RabbitProducerTest {
    private static final Logger logger = LoggerFactory.getLogger(RabbitProducerTest.class);

    public static void main(String[] args) {
        RabbitMqProducer producer = new RabbitMqProducer();

        BDPSyncMessageBody body1 = new BDPSyncMessageBody("xform_0001", "fm_dnsandnsandjsnajndajnnn_xform_0001", BDPSyncMessageBody.DATA_CHANGE);
        FormTriggerMessageBody body2 = new FormTriggerMessageBody();
        body2.setTenantId("xform_0002");
        body2.setFormId("fm_0002");
        body2.setName("TriggerMessage");
        body2.setTriggerEvent(2);

        ViewMessageBody body3 = new ViewMessageBody();
        body3.setTenantId("xform_0003");
        body3.setType(2);
        body3.setDataIds(null);


        RabbitMqMessage messageForFanout = new RabbitMqMessage("fanout_exchange","fanout_queue","a",body1);
        RabbitMqMessage messageForFanout2 = new RabbitMqMessage("fanout_exchange","fanout_queue","b",body1);

        RabbitMqMessage messageForDirect = new RabbitMqMessage("direct_exchange","direct_queue","direct",body2);
        RabbitMqMessage messageForDirect2 = new RabbitMqMessage("direct_exchange","direct_queue","direct2",body2);

        RabbitMqMessage messageForTopic = new RabbitMqMessage("topic_exchange","topic_queue","topic.test",body3);
        RabbitMqMessage messageForTopic2 = new RabbitMqMessage("topic_exchange","topic_queue","task.ack",body3);

        producer.send(messageForFanout);
        producer.send(messageForFanout2);
        producer.send(messageForDirect);
        producer.send(messageForDirect2);
        producer.send(messageForTopic);
        producer.send(messageForTopic2);

        producer.closeConnection();
    }

}
