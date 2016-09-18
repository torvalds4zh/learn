package com.weibangong.camel.jmx;

import org.apache.camel.builder.RouteBuilder;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

/**
 * Created by chenbo on 16/8/29.
 */
public class MyRouteBuilder extends RouteBuilder {
    private SimpleBean bean;
    private MBeanServer server;

    public MyRouteBuilder() throws Exception{
        server = ManagementFactory.getPlatformMBeanServer();
        bean = new SimpleBean();

        server.registerMBean(bean, new ObjectName("jmxExample", "name", "simpleBean"));
    }

    @Override
    public void configure() throws Exception {
        from("jmx:platform?objectDomain=jmxExample&key.name=simpleBean")
                .to("log:jmxEvent");

        from("timer:foo?period=6000").bean(bean, "tick");
    }
}
