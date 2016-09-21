package com.weibangong.framework.configadmin;

import com.weibangong.framework.configadmin.internal.ZookeeperManagedService;
import lombok.extern.slf4j.Slf4j;
import org.osgi.framework.*;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.cm.ManagedService;
import org.osgi.util.tracker.ServiceTracker;

import java.util.Dictionary;
import java.util.Hashtable;

/**
 * Created by jianghailong on 15/11/11.
 */
@Slf4j
public class Activator implements BundleActivator {

    private ConfigurationAdminTrack cmTrack;

    @Override
    public void start(BundleContext bundleContext) throws Exception {
        cmTrack = new ConfigurationAdminTrack(bundleContext);
        cmTrack.open();
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        if (cmTrack != null) {
            cmTrack.close();
        }
    }

    private class ConfigurationAdminTrack extends ServiceTracker {

        private ServiceRegistration<ManagedService> registration;
        private BundleContext bundleContext;

        public ConfigurationAdminTrack(BundleContext bundleContext) {
            super(bundleContext, ConfigurationAdmin.class.getName(), null);
            this.bundleContext = bundleContext;
        }

        @Override
        public Object addingService(ServiceReference reference) {
            Object configurationAdmin = super.addingService(reference);
            if (configurationAdmin instanceof ConfigurationAdmin) {
                ZookeeperManagedService managedService = new ZookeeperManagedService();
                managedService.setConfigurationAdmin((ConfigurationAdmin) configurationAdmin);

                Dictionary<String, String> props = new Hashtable<>();
                props.put(Constants.SERVICE_PID, ServiceProperties.PID);
                registration = bundleContext.registerService(ManagedService.class, managedService, props);
                log.debug("register service:{0}",ZookeeperManagedService.class.getName());
            }
            return configurationAdmin;
        }

        @Override
        public void removedService(ServiceReference reference, Object service) {
            if (service instanceof ConfigurationAdmin && registration != null) {
                registration.unregister();
                registration = null;
            }

            super.removedService(reference, service);
        }
    }
}
