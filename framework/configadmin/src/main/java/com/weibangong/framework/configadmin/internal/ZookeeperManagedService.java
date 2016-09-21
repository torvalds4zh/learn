package com.weibangong.framework.configadmin.internal;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.KeeperException;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedService;
import com.weibangong.framework.configadmin.ServiceProperties;

import java.io.IOException;
import java.util.Dictionary;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by jianghailong on 15/11/11.
 */
@Slf4j
public class ZookeeperManagedService implements ManagedService {

    private ConfigurationAdmin configurationAdmin;

    private Lock lock = new ReentrantLock();

    private ZNodeMonitor monitor;
    private GlobalZNodeMonitor globalZNodeMonitor;

    public void setConfigurationAdmin(ConfigurationAdmin configurationAdmin) {
        try {
            lock.lock();

            log.info("Setting configurationadmin to {}", configurationAdmin);
            this.configurationAdmin = configurationAdmin;
        } finally {
            lock.unlock();
        }
    }

    public ConfigurationAdmin getConfigurationAdmin() {
        return configurationAdmin;
    }

    @Override
    public synchronized void updated(Dictionary<String, ?> dictionary) throws ConfigurationException {
        ZookeeperConfig config = validationConfiguration(dictionary);
        if (config == null) {
            return;
        }

        try {
            if (monitor != null) {
                monitor.destroy();
                monitor = null;
            }
            log.info("initialization ZNodeMonitor...");
            monitor = new ZNodeMonitor(configurationAdmin, config);
            log.info("ZNodeMonitor initialization is complete...");
//            if(globalZNodeMonitor!=null){
//                globalZNodeMonitor.destroy();
//                globalZNodeMonitor=null;
//            }
//
//            log.info("initialization GlobalZNodeMonitor...");
//            globalZNodeMonitor=new GlobalZNodeMonitor(configurationAdmin,config);
//            log.info("GlobalZNodeMonitor initialization is complete...");

        } catch (IOException e) {
            throw new ConfigurationException("", e.getMessage(), e);
        } catch (InterruptedException e) {
            throw new ConfigurationException("", e.getMessage(), e);
        } catch (KeeperException e) {
            throw new ConfigurationException("", e.getMessage(), e);
        }
    }

    private ZookeeperConfig validationConfiguration(Dictionary<String, ?> dictionary) throws ConfigurationException {
        String url = System.getProperty(ServiceProperties.URL);
        String timeoutString = System.getProperty(ServiceProperties.TIMEOUT);
        String zNode = "/";
        String filter = null;
        if (dictionary != null) {
            if (dictionary.get(ServiceProperties.URL) != null) {
                url = (String) dictionary.get(ServiceProperties.URL);
            }
            if (dictionary.get(ServiceProperties.TIMEOUT) != null) {
                timeoutString = (String) dictionary.get(ServiceProperties.TIMEOUT);
            }
            if (dictionary.get(ServiceProperties.ZNODE) != null) {
                zNode = (String) dictionary.get(ServiceProperties.ZNODE);
                zNode = zNode.endsWith("/") ? zNode.substring(0, zNode.length() - 1) : zNode;
            }
            if (dictionary.get(ServiceProperties.FILTER) != null) {
                filter = (String) dictionary.get(ServiceProperties.FILTER);
            }
        }

        if (url == null || url.length() == 0) {
            return null;
        }

        int timeout = 30000;
        if (timeoutString != null) {
            try {
                timeout = Integer.parseInt(timeoutString);
            } catch (NumberFormatException e) {
                throw new ConfigurationException(ServiceProperties.TIMEOUT, "timeout must is number.", e);
            }
        }

        return new ZookeeperConfig(url, timeout, zNode, filter);
    }
}
