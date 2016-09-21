package com.weibangong.message.transfer.service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mqtt.MQTTConfiguration;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by chenbo on 16/09/21.
 */
@Data
@Slf4j
public class MsgStreamService {

    private CamelContext camelContext;

    public void publish(Long targetId, String payload) {
        log.info("targetId: {} , payload: {}", targetId,payload);
        Map<String, Object> headers = new HashMap();
        headers.put("priority", 9);
        headers.put("Topic", "u/" + targetId);
        template().requestBodyAndHeaders("direct:msg", payload, headers);
    }

    public void ack(Long sourceId, String payload) {
        downPublish("u/" + sourceId, payload);
    }

    public void downPublish(String topic, String payload) {
        template().requestBodyAndHeader("direct:delivery", payload,
                MQTTConfiguration.MQTT_PUBLISH_TOPIC, topic);
    }

//    public void solrSync(String message) {
//        template().asyncRequestBodyAndHeader("direct:solrSync", message, "_ACTION", "INDEX");
//    }

    public void distributor(String alert, Long userId) {
        distributor(alert, Collections.singleton(userId), null, null, null);
    }

    public void distributor(String alert, Long userId, String sound, String properties, String notifyId) {
        distributor(alert, Collections.singleton(userId), sound, properties, notifyId);
    }

    public void distributor(String alert, Set<Long> userIds, String sound, String properties, String notifyId) {
        StringBuilder builder = new StringBuilder();
        for (Long userId : userIds) {
            builder.append(userId).append(",");
        }
        String requestUserIds = builder.substring(0, builder.length() - 1);
        Map<String, Object> headers = new HashMap();
        headers.put("userIds", requestUserIds);

        if (properties != null && !properties.isEmpty()) {
            headers.put("properties", properties);
        }

        if (sound != null && !sound.isEmpty()) {
            headers.put("sound", sound);
        }

        if (notifyId != null && !notifyId.isEmpty()) {
            headers.put("notifyId", notifyId);
        }

        template().requestBodyAndHeaders("direct:distributor", alert, headers);
    }


//    public void robot(String message) {
//        template().requestBody("direct:robot",message);
//    }

    private ProducerTemplate template;

    private ProducerTemplate template() {
        if (template == null) {
            template = camelContext.createProducerTemplate();
        }
        return template;
    }
}
