package com.weibangong.message.transfer.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.weibangong.message.transfer.mapper.DocumentMapper;
import com.weibangong.message.transfer.mapper.MessageMapper;
import com.weibangong.message.transfer.model.Document;
import com.weibangong.message.transfer.model.Message;
import com.weibangong.message.transfer.model.RequestParameter;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by jianghailong on 16/1/4.
 */
@Data
@Slf4j
public class StorageService {

    private final Cache<String, Message> dupRemoveCaches = CacheBuilder.newBuilder().maximumSize(200000)
            .expireAfterWrite(1, TimeUnit.DAYS).build();

    private ObjectMapper objectMapper;

    private MsgStreamService streamService;

    private MessageMapper messageMapper;

    private DocumentMapper documentMapper;

    private SequenceService sequenceService;

    private JedisSentinelPool pool;

    public StorageService() {
        JsonFactory jsonFactory = new JsonFactory();
        jsonFactory.enable(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS);

        objectMapper = new ObjectMapper(jsonFactory);
        objectMapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
        objectMapper.enable(SerializationFeature.WRITE_ENUMS_USING_INDEX);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public Message storage(Message message) {
        message.setId(null);
        message.setCreatedAt(new Date());
        messageMapper.insert(message);

        if (message.getContentType() == Message.ContentType.DOC) {
            try {
                createDocumentRecord(message);
            } catch (Exception e) {
                log.error("Insert document error!", e);
            }
        }
        return message;
    }

    public Message getCachingSentMessage(final Message message) {
        assert message != null;

        if (message.getUuid() == null) {
            return null;
        }
        Message existsMessage = dupRemoveCaches.getIfPresent(message.getUuid());
        if (existsMessage == null) {
            Jedis jedis = null;
            try {
                jedis = pool.getResource();
                String exists = jedis.get(message.getUuid());
                if (exists != null) {
                    existsMessage = objectMapper.readValue(exists.getBytes(), Message.class);
                }
            } catch (Exception e) {
                log.warn("read redis message error. ", e);
            } finally {
                if (jedis != null) {
                    pool.returnResourceObject(jedis);
                }
            }
        }
        return existsMessage;
    }

//    public void cacheSentMessage(final Message message) {
//        assert message != null;
//
//        if (message.getContentType() == Message.ContentType.TEXT) {
//            Map<String, Object> solrMsg = new HashMap<>();
//            solrMsg.put("db_id", message.getId());
//            solrMsg.put("tid", message.getTenantId());
//            solrMsg.put("createAt", message.getCreatedAt());
//            solrMsg.put("contentType", message.getContentType());
//            solrMsg.put("content", message.getContent());
//            solrMsg.put("targetId", message.getTargetId());
//            solrMsg.put("targetType", message.getTargetType());
//            solrMsg.put("sourceId", message.getSourceId());
//            solrMsg.put("sourceType", 2);
//            try {
//                streamService.solrSync(objectMapper.writeValueAsString(solrMsg));
//            } catch (Exception e) {
//                log.error("Can not sync to solr! message : {}", message, e);
//            }
//        }
//
//        if (message.getUuid() == null) {
//            return;
//        }
//
//        dupRemoveCaches.put(message.getUuid(), message);
//        Jedis jedis = null;
//        try {
//            jedis = pool.getResource();
//            jedis.setex(message.getUuid(), 600, objectMapper.writeValueAsString(message));
//        } catch (Exception e) {
//            log.warn("read redis message error. ", e);
//        } finally {
//            if (jedis != null) {
//                pool.returnResourceObject(jedis);
//            }
//        }
//    }

    public void acknowledge(final Message message, Exchange exchange) {
        Message ack = new Message();
        ack.setId(message.getId());
        ack.setUuid(message.getUuid());
        ack.setCreatedAt(message.getCreatedAt());

        RequestParameter parameter = new RequestParameter();
        parameter.setAction("messages/ack");
        parameter.setContent(ack);
        try {
            String payload = objectMapper.writeValueAsString(parameter);
            streamService.ack(message.getSourceId(), payload);
            exchange.getOut().setBody(objectMapper.writeValueAsString(message));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void createDocumentRecord(Message message) {
        Long now = System.currentTimeMillis();
        Document document = new Document();
        document.setTenantId(message.getTenantId());
        document.setCreatedById(message.getCreatedById());
        document.setCreatedAt(now);
        document.setUpdatedById(message.getUpdatedById());
        document.setUpdatedAt(now);
        document.setFileMetaData(message.getContent().substring(0, message.getContent().length() - 1) +
                ",\"createdAt\":\"" + now + "\"}");
        document.setCarrierId(message.getId());
        document.setCarrierType(0);
        document.setSourceId(message.getSourceId());
        document.setSourceType(message.getSourceType());
        document.setTargetId(message.getTargetId());
        document.setTargetType(message.getTargetType());
        document.setStatus(0);
        documentMapper.insert(document);
    }
}
