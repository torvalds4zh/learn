package com.weibangong.message.transfer.processor;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.weibangong.message.transfer.mapper.PrincipalMapper;
import com.weibangong.message.transfer.model.Message;
import com.weibangong.message.transfer.model.Principal;
import com.weibangong.message.transfer.model.PrincipalType;
import com.weibangong.message.transfer.model.RequestParameter;
import com.weibangong.message.transfer.router.MessageRouter;
import com.weibangong.message.transfer.service.StorageService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by jianghailong on 16/1/4.
 */
@Slf4j
@Data
public class ReceiveProcessor implements Processor {

    private Map<Integer, MessageRouter> routers;

    private PrincipalMapper principalMapper;

    private StorageService storageService;

    private ObjectMapper objectMapper = new ObjectMapper();

    private static Cache<Long, PrincipalType> principalTypeCache = CacheBuilder.newBuilder().expireAfterWrite(14L, TimeUnit.DAYS).build();

    public ReceiveProcessor() {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        long beforeTimeMillis = System.currentTimeMillis();

        String receiveBody = exchange.getIn().getBody(String.class);
        log.info("Message arrived body: {}", receiveBody);

        RequestParameter request = objectMapper.readValue(receiveBody, RequestParameter.class);
        if (request == null || request.getContent() == null) {
            return;
        }

        PrincipalType sourceType = getPrincipalType(request.getContent().getSourceId());
        if (sourceType == null || sourceType.ordinal() == 7) {
            return;
        }

        PrincipalType targetType = getPrincipalType(request.getContent().getTargetId());

        if (targetType == null) {
           return;
        }
        request.getContent().setTargetType(targetType.ordinal());

        MessageRouter messageRouter = routers.get(targetType.ordinal());
        if (messageRouter == null) {
            log.info("Can not find route service for the target type: " + request.getContent().getContentType() + ".");
            return;
        }

        Message cachingSentMessage = storageService.getCachingSentMessage(request.getContent());
        if (cachingSentMessage == null) {
            storageService.storage(request.getContent());
            storageService.acknowledge(request.getContent(), exchange);
//            storageService.cacheSentMessage(request.getContent());
            messageRouter.process(request.getContent());
        } else {
            storageService.acknowledge(request.getContent(), exchange);
        }
        log.info("Performance: Message UUID: {}, storage message {}ms",
                request.getContent().getUuid(), System.currentTimeMillis() - beforeTimeMillis);
    }

    private PrincipalType getPrincipalType(Long principalId) {
        PrincipalType cachedPrincipalType = principalTypeCache.getIfPresent(principalId);
        if (cachedPrincipalType != null) {
            return cachedPrincipalType;
        }

        Principal principal = principalMapper.selectByPrimaryKey(principalId);
        if (principal == null) {
            return null;
        } else {
            principalTypeCache.put(principalId, principal.getType());
            return principal.getType();
        }
    }
}
