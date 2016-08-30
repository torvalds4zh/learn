package com.weibangong.camel.cxf.provider;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by chenbo on 16/8/30.
 */
public class Server {

    AbstractApplicationContext applicationContext;

    public void start() throws Exception{
        System.setProperty("port", "9000");

        //使用spring启动camel容器
        applicationContext = new ClassPathXmlApplicationContext("/META-INF/spring/CamelCXFProviderRouteConfig.xml");

    }

    public void stop(){
        if(applicationContext != null){
            applicationContext.stop();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Server server = new Server();
        System.out.println("Server ready...");

        Thread.sleep(5 * 60 * 1000);
        server.stop();
        System.out.println("Server exiting");
        System.exit(0);
    }

}
