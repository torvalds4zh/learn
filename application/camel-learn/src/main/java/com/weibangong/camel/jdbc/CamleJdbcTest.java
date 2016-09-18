package com.weibangong.camel.jdbc;

import org.apache.camel.ConsumerTemplate;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.Route;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by chenbo on 16/8/30.
 */
public class CamleJdbcTest extends CamelSpringTestSupport {
    @Override
    protected AbstractApplicationContext createApplicationContext() {
        return new ClassPathXmlApplicationContext("/META-INF/spring/camel-context.xml");
    }

    @Test
    public void testOn(){
        System.out.println("====start====");
        ProducerTemplate producerTemplate = context.createProducerTemplate();

        List<Route> routeList = context.getRoutes();
        for (Route route : routeList){
            ConsumerTemplate consumerTemplate = context.createConsumerTemplate();
            Exchange receive = consumerTemplate.receive(route.getEndpoint());
            String body = receive.getIn().getBody(String.class);
            System.out.println("consume " + body);
        }
    }

    @Test
    public void testaa(){
        Set<Integer> abd = new HashSet<Integer>();
        abd.add(1);
        abd.add(2);
        System.out.println(abd);
    }
}
