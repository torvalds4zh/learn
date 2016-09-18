package com.weibangong.osgi.felix;


import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

/**
 * Created by chenbo on 16/8/26.
 */
public class MyEventHandler implements EventHandler {

    public void handleEvent(Event event) {
        System.out.println("test event received, topic: " +event.getTopic() +", contain properties: " + event.getPropertyNames());
        try {
            Thread.currentThread().sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
