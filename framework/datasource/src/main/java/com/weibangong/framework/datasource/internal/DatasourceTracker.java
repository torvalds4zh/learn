package com.weibangong.framework.datasource.internal;

import com.weibangong.framework.datasource.DatasourceConstants;
import org.osgi.framework.BundleContext;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedServiceFactory;

import java.util.Dictionary;

/**
 * Created by jianghailong on 15/12/12.
 */
public class DatasourceTracker implements ManagedServiceFactory {

    private final DatasourceContainer container;

    public DatasourceTracker(BundleContext bundleContext) {
        container = new DatasourceContainer(bundleContext);
    }

    @Override
    public String getName() {
        return DatasourceConstants.PID;
    }

    @Override
    public void updated(String s, Dictionary<String, ?> dictionary) throws ConfigurationException {
        try {
            container.applyConfigure(s, dictionary);
        } catch (Exception e) {
            throw new ConfigurationException("property", "starting", e);
        }
    }

    @Override
    public void deleted(String s) {
        container.removeConfigure(s);
    }
}
