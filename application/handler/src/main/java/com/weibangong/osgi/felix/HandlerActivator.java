package com.weibangong.osgi.felix;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;

/**
 * Created by chenbo on 16/8/26.
 */
public class HandlerActivator implements BundleActivator {
    private ServiceReference serviceReference;

    EventAdmin eventAdmin = null;

    HashMap props = null;

    Event event = null;

    ServiceRegistration registration;

    /**
     * event  topic
     */
    final static String[] topic = {"my_osgi_test_event"};

    public void start(BundleContext bundleContext) throws Exception {
        System.out.println("HandleActivator start...");
        Dictionary dictionary = new Hashtable();
        dictionary.put(EventConstants.EVENT_TOPIC, topic);
        EventHandler eventHandler = new MyEventHandler();

        //registering the event handler
        registration = bundleContext.registerService(EventHandler.class, eventHandler, dictionary);
        if(registration != null){
            System.out.println("event handler registered.");
        }
    }

    public void stop(BundleContext bundleContext) throws Exception {
        if(registration != null){
            registration.unregister();
            System.out.println("event handler unregistered.");
        }
    }
}
