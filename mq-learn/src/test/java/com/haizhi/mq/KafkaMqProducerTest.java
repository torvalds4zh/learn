package com.haizhi.mq;

import com.haizhi.mq.test.custom.User;
import com.haizhi.mq.test.kafka.KafkaMqProducer;
import com.haizhi.mq.test.MqMessage;
import com.haizhi.mq.test.MsgProducer;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenbo on 15/11/11.
 */
public class KafkaMqProducerTest {
    private MsgProducer producer;

    @Before
    public void setUp(){
        producer = new KafkaMqProducer();
    }

    @Test
    public void testSetUp(){
        MqMessage mqMessage = new MqMessage();
        mqMessage.setTopic("mqTest");
        mqMessage.setTag("mqTestTag");
        mqMessage.setKey("xform_test");
        Map<String,Object> msgMap = new HashMap<String, Object>();
        msgMap.put("method", "create");
        msgMap.put("triggerId", "trigger_0001");
        msgMap.put("user", new User(1, "chenbo", new Date()));
        mqMessage.setContent(msgMap);

        MqMessage mqMessage2 = new MqMessage();
        mqMessage2.setTopic("mqTest");
        mqMessage2.setTag("mqTestTag2");
        mqMessage2.setKey("xform_test2");
        Map<String,Object> msgMap2 = new HashMap<String, Object>();
        msgMap2.put("method","delete");
        msgMap2.put("triggerId", "trigger_0002");
        msgMap2.put("user", new User(2, "guanye", new Date()));
        mqMessage2.setContent(msgMap2);

        MqMessage mqMessage3 = new MqMessage();
        mqMessage3.setTopic("mqTest");
        mqMessage3.setTag("mqTestTag3");
        mqMessage3.setKey("xform_test3");
        Map<String,Object> msgMap3 = new HashMap<String, Object>();
        msgMap3.put("method","update");
        msgMap3.put("triggerId", "trigger_0003");
        msgMap3.put("user", new User(3, "yangdeng", new Date()));
        mqMessage3.setContent(msgMap3);

        producer.send(mqMessage);
        producer.send(mqMessage2);
        producer.send(mqMessage3);

    }
}
