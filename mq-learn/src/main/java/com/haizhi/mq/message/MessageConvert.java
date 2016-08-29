package com.haizhi.mq.message;

import com.haizhi.mq.utils.JsonUtils;
import kafka.message.MessageAndMetadata;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by chenbo on 16/3/30.
 */
public class MessageConvert {

    private static Logger logger = LoggerFactory.getLogger(MessageConvert.class);

    public static byte[] objectToByte(Object obj) {
        byte[] bytes = null;
        try {
            // object to bytearray
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream oo = new ObjectOutputStream(bo);
            oo.writeObject(obj);

            bytes = bo.toByteArray();

            bo.close();
            oo.close();
        } catch (Exception e) {
            logger.error("error occurs while translating Object to byte[].", e);
        }
        return bytes;
    }

    public static Object byteToObject(byte[] bytes) {
        Object obj = null;
        try {
            // bytearray to object
            ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
            ObjectInputStream oi = new ObjectInputStream(bi);

            obj = oi.readObject();
            bi.close();
            oi.close();
        } catch (Exception e) {
            logger.error("error occurs while translating byte[] to Object.", e);
        }
        return obj;
    }

    public static ProducerRecord<String,Object> convertKafkaMsg(KafkaMessage message){
        ProducerRecord<String,Object> record = new ProducerRecord(message.getTopic(), message.getKey(), JsonUtils.encode(message.getValue()));
        return record;
    }

    public static KafkaMessage convertToKafkaMessage(MessageAndMetadata<byte[], byte[]> messageAndMetadata){
        Object key = messageAndMetadata.key();
        Object value = messageAndMetadata.message();

        KafkaMessage mqMessage = new KafkaMessage(messageAndMetadata.topic(), key, value);

        return mqMessage;
    }
}
