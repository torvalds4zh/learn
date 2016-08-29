package com.haizhi.mq.test;

import com.haizhi.mq.utils.JsonUtils;
import com.haizhi.mq.utils.ParameterizedTypeReference;
import kafka.message.MessageAndMetadata;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * 消息转换器
 *
 * Created by xiaolezheng on 15/8/27.
 */
public final class MsgConvert {
    public static com.alibaba.rocketmq.common.message.Message convertRocketMsg(MqMessage mqMessage){
        com.alibaba.rocketmq.common.message.Message msg = new com.alibaba.rocketmq.common.message.Message();
        msg.setTopic(mqMessage.getTopic());
        msg.setTags(mqMessage.getTag());
        msg.setKeys(String.valueOf(mqMessage.getKey()));
        msg.setBody(JsonUtils.encode(mqMessage.getContent()).getBytes());

        return msg;
    }

    public static MqMessage convert(com.alibaba.rocketmq.common.message.Message message){
        MqMessage msg = MqMessage.create(message.getTopic());

        String msgBody = new String(message.getBody(), MsgConstant.UTF_8);
        Map<String,Object> content= JsonUtils.decode(msgBody,Map.class);

        msg.setContent(content);
        msg.setTag(message.getTags());
        msg.setKey(message.getKeys());


        return msg;
    }

    public static ProducerRecord<String,Object> convertKafkaMsg(MqMessage mqMessage){
        ProducerRecord<String,Object> record = new ProducerRecord(mqMessage.getTopic(), mqMessage.getKey(), JsonUtils.encode(mqMessage.getContent()));
        return record;
    }

    public static MqMessage convert(MessageAndMetadata<byte[], byte[]> messageAndMetadata){
        String key = new String(messageAndMetadata.key(), MsgConstant.UTF_8);
        String content = new String(messageAndMetadata.message(), MsgConstant.UTF_8);

        MqMessage mqMessage = MqMessage.create(messageAndMetadata.topic());
        mqMessage.setKey(key);
        mqMessage.setContent(JsonUtils.decode(content, new ParameterizedTypeReference<Map<String, Object>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        }));

        return mqMessage;
    }

}
