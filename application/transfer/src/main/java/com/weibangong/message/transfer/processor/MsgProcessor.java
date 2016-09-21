package com.weibangong.message.transfer.processor;

import com.weibangong.message.client.MessageConstants;
import com.weibangong.message.transfer.service.MsgStreamService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

@Slf4j
@Data
public class MsgProcessor implements Processor {

    private MsgStreamService streamService;

    public void process(Exchange exchange) throws Exception {
        String msg = exchange.getIn().getBody(String.class);
        String topic = exchange.getIn().getHeader(MessageConstants.MQTT.TOPIC, String.class);
        if (topic != null && topic.equals("u/0")) {
            log.warn("topic u/0 incorrect");
            return;
        }
        streamService.downPublish(topic, msg);
    }
}
