package com.weibangong.camel.jdbc;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by chenbo on 16/8/30.
 */
public class RecordProcessor implements Processor {
    private static Logger log = LoggerFactory.getLogger(RecordProcessor.class);

    public void process(Exchange exchange) throws Exception {
        log.trace("Processing msg {}", exchange);
        Map<String, Object> record = exchange.getIn().getBody(Map.class);
        log.info("Processing record {}", record);
    }
}
