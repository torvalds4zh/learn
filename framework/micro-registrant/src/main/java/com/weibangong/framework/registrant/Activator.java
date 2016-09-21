package com.weibangong.framework.registrant;

import lombok.extern.slf4j.Slf4j;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import com.weibangong.framework.registrant.internal.MachineService;

import java.rmi.Remote;

/**
 * Created by jianghailong on 16/5/3.
 */
@Slf4j
public class Activator implements BundleActivator {

    @Override
    public void start(BundleContext bundleContext) throws Exception {
        // tracker zookeeper connect.
        String host = new MachineService().getHostAddress();
        int port = new MachineService().getPort();
        ServiceReference<?>[] references = bundleContext.getAllServiceReferences(Remote.class.getName(), null);
        // register with zookeeper.
        // tracker remote service register/unregister.
    }

    @Override
    public void stop(BundleContext context) throws Exception {
    }
}
