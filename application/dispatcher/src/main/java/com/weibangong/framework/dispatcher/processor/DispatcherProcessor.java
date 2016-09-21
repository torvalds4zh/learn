package com.weibangong.framework.dispatcher.processor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.spi.Synchronization;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * Created by jianghailong on 15/12/28.
 */
@Slf4j
public class DispatcherProcessor implements Processor {

    private static final String REQUEST_KEY = "CamelRequestKey";
    private static final ObjectMapper mapper = new ObjectMapper();

    private String host;
    private ProducerTemplate template;

    public DispatcherProcessor(String host) {
        if (host.endsWith("/")) {
            host = host.substring(0, host.length() - 1);
        }
        this.host = host;
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        if (template == null) {
            template = exchange.getContext().createProducerTemplate();
        }

        final Map<String, Object> response = new HashMap<>();

        String body = exchange.getIn().getBody(String.class);
        JsonNode node = mapper.readTree(body);

        final CountDownLatch latch = new CountDownLatch(node.size());
        Iterator<String> iterator = node.fieldNames();
        while (iterator.hasNext()) {
            String requestKey = iterator.next();
            JsonNode requestNode = node.get(requestKey);

            String method = requestNode.get("method").asText().toUpperCase();
            String path = requestNode.get("path").asText();
            String httpBody = null;
            if (requestNode.has("body")) {
                httpBody = requestNode.get("body").asText();
            }
            if (!path.startsWith("/")) {
                path = "/" + path;
            }
            log.info("{} {}", method, path);

            Endpoint endpoint = exchange.getContext().getEndpoint("ahc:" + host + path);
            final Exchange ex = endpoint.createExchange();
            for (String headerKey : exchange.getIn().getHeaders().keySet()) {
                if (!headerKey.startsWith("Camel")) {
                    ex.getIn().setHeader(headerKey, exchange.getIn().getHeader(headerKey));
                }
            }
            ex.getIn().setHeader(REQUEST_KEY, requestKey);
            ex.getIn().setHeader(Exchange.HTTP_METHOD, method);
            ex.getIn().setBody(httpBody);

            template.asyncCallback(endpoint, ex, new Synchronization() {

                @Override
                public void onComplete(Exchange completeExchange) {
                    String responseBody = completeExchange.getOut().getBody(String.class);
                    try {
                        response.put(completeExchange.getIn().getHeader(REQUEST_KEY, String.class), mapper.readTree(responseBody));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    latch.countDown();
                }

                @Override
                public void onFailure(Exchange failureExchange) {
                    System.out.println(failureExchange);
                    latch.countDown();
                }
            });
        }
        latch.await();
        exchange.getOut().setBody(response);
    }
}
