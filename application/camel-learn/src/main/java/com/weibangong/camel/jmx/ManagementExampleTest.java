package com.weibangong.camel.jmx;

import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.Test;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.util.Set;

/**
 * Created by chenbo on 16/8/29.
 */
public class ManagementExampleTest extends CamelSpringTestSupport{
    protected AbstractXmlApplicationContext createApplicationContext(){
        return new ClassPathXmlApplicationContext("META-INF/spring/camel-context.xml");
    }

    @Test
    public void testManagementExample() throws Exception{

        Thread.sleep(2000);

        MBeanServer mBeanServer = context.getManagementStrategy().getManagementAgent().getMBeanServer();

        Set<ObjectName> set = mBeanServer.queryNames(new ObjectName("*:type=endpoints,*"), null);

        System.out.println(set);

        set = mBeanServer.queryNames(new ObjectName("*:type=routes,*"), null);

        System.out.println(set);

        for (ObjectName name : set){
            mBeanServer.invoke(name, "stop", null, null);
        }
    }

}
