package com.weibangong.framework.datasource;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.ManagedServiceFactory;
import com.weibangong.framework.datasource.internal.DatasourceTracker;

import java.util.Dictionary;
import java.util.Hashtable;

/**
 * Created by jianghailong on 15/12/12.
 */
public class Activator implements BundleActivator {

    private ServiceRegistration<?> dsTracker;

    @Override
    public void start(BundleContext bundleContext) throws Exception {
        registerConfigAdminSupport(bundleContext);
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        if (dsTracker != null) {
            dsTracker.unregister();
        }
    }

    private void registerConfigAdminSupport(BundleContext context) {
        DatasourceTracker tracker = new DatasourceTracker(context);
        Dictionary<String, String> properties = new Hashtable<>();
        properties.put(Constants.SERVICE_PID, tracker.getName());
        dsTracker = context.registerService(ManagedServiceFactory.class.getName(), tracker, properties);
    }
}
