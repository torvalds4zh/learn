package com.weibangong.osgi.felix;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.event.EventConstants;

import java.util.HashMap;

/**
 * Created by chenbo on 16/8/26.
 */
public class PusherActivator implements BundleActivator {
    private BundleContext context;

    private ServiceReference serviceReference;

    EventAdmin eventAdmin = null;

    HashMap props = null;

    Event event = null;

    boolean flag = true;

    public void start(BundleContext bundleContext) throws Exception {
        this.context = bundleContext;
        serviceReference = context.getServiceReference(EventAdmin.class);

        if (serviceReference == null) {
            throw new Exception("Failed to obtain EventAdmin service references!");
        }
        eventAdmin = (EventAdmin) context.getService(serviceReference);
        if (eventAdmin == null) {
            throw new Exception("Failed to obtain EventAdmin!");
        }

        int count = 0;
        while (flag) {
            props = new HashMap();
            props.put(EventConstants.BUNDLE_SYMBOLICNAME, "est.first");

            //Create a topic
            event = new Event("my_osgi_test_event", props);
            eventAdmin.postEvent(event);
            System.out.println("======Send Event======");
            try {
                Thread.sleep(3000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            count += 1;
            if (count == 3) {
                flag = false;
            }
        }
    }

    public void stop(BundleContext bundleContext) throws Exception {
        System.out.println("PusherActivator stopping...");
    }
}
