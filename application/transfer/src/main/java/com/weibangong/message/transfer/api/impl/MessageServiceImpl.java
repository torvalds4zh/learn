package com.weibangong.message.transfer.api.impl;

import com.weibangong.message.transfer.api.MessageRestService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import com.weibangong.framework.auth.interceptor.RequestAuthentication;

@Slf4j
@Data
public class MessageServiceImpl implements MessageRestService {

    private CamelContext camelContext;

    @RequestAuthentication
    public String messageAdd(String param) {
        try {
            return camelContext.createProducerTemplate().requestBody("direct:httpMessageAdd", param, String.class);
        } catch (Exception e) {
            log.error("Http : messageAdd error!", e);
            return "{\"status\":\"0\"}";
        }
    }

    @RequestAuthentication
    public String messageCancel(String param) {
        return httpMessageService(param);
    }

    private String httpMessageService(String param){
        try {
            return camelContext.createProducerTemplate().requestBody("direct:httpMessageService", param, String.class);
        } catch (Exception e) {
            log.error("Http : messageService error!", e);
            return "{\"status\":\"0\"}";
        }
    }
}