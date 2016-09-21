package com.weibangong.framework.configadmin.internal;

import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import com.weibangong.framework.configadmin.ServiceProperties;

import java.io.IOException;

/**
 * Created by jianghailong on 15/11/20.
 */
public class ConfigurationUtils {


    public static Configuration findConfiguration(
            ConfigurationAdmin configurationAdmin, String servicePid) {
        Configuration configuration = null;
        try {
            if (!servicePid.contains("-")) {
                configuration = configurationAdmin.getConfiguration(servicePid, null);
            } else {
                String factoryId = servicePid.substring(0, servicePid.indexOf("-"));
                Configuration[] configurations = configurationAdmin.listConfigurations(null);
                for (Configuration config : configurations) {
                    if (config.getPid().equalsIgnoreCase(servicePid)) {
                        configuration = config;
                    } else if (config.getFactoryPid() != null &&
                            (config.getFactoryPid().equalsIgnoreCase(servicePid) || config.getFactoryPid().equalsIgnoreCase(factoryId))) {
                        if (config.getProperties() != null) {
                            String propertyId = (String) config.getProperties().get(ServiceProperties.SERVICE_PID);
                            if (propertyId != null && propertyId.equalsIgnoreCase(servicePid)) {
                                configuration = config;
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidSyntaxException e) {
            e.printStackTrace();
        }
        return configuration;
    }
}
