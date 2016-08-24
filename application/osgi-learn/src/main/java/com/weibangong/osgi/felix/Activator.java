package com.weibangong.osgi.felix;

import org.osgi.framework.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by chenbo on 16/8/22.
 */
public class Activator implements BundleActivator, ServiceListener {
    private static Logger logger = LoggerFactory.getLogger(Activator.class);
    public void start(BundleContext bundleContext) throws Exception {
        System.out.println("Starting to listen for service events.");
        bundleContext.addServiceListener(this);
    }

    public void stop(BundleContext bundleContext) throws Exception {
        bundleContext.removeServiceListener(this);
        System.out.println("Stopped listening for service events.");
    }

    public void serviceChanged(ServiceEvent serviceEvent) {
        String[] objectClass = (String[])serviceEvent.getServiceReference().getProperty("objectClass");
        if (serviceEvent.getType() == ServiceEvent.REGISTERED) {
            System.out.println("Ex1: Service of type " + objectClass[0] + " registered.");
        } else if (serviceEvent.getType() == ServiceEvent.UNREGISTERING) {
            System.out.println("Ex1: Service of type " + objectClass[0] + " registered.");
        } else if (serviceEvent.getType() == ServiceEvent.MODIFIED) {
            System.out.println("Ex1: Service of type " + objectClass[0] + " modified.");
        }
    }
}
