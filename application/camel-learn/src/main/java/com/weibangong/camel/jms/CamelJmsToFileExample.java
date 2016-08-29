package com.weibangong.camel.jms;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

import javax.jms.ConnectionFactory;

/**
 * Created by chenbo on 16/8/29.
 */
public class CamelJmsToFileExample {
    private static String ACTIVEMQ_BRAOKER_URL = "vm://localhost?broker.persistent=false";

    public static void main(String[] args) throws Exception {

        CamelContext context = new DefaultCamelContext();

        ConnectionFactory factory = new ActiveMQConnectionFactory();

        context.addComponent("test-jms", JmsComponent.jmsComponentAutoAcknowledge(factory));

        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("test-jms:queue:test.queue").to("file://target/test");
            }
        });

        ProducerTemplate template = context.createProducerTemplate();

        context.start();


        for (int i = 0; i < 10; i++) {
            template.sendBody("test-jms:queue:test.queue", "Test Message: " + i);
        }

        Thread.sleep(1000);
        context.stop();
    }
}
