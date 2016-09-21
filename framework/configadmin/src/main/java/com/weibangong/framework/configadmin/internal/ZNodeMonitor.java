package com.weibangong.framework.configadmin.internal;

import com.weibangong.framework.configadmin.ServiceProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by jianghailong on 15/11/19.
 */
@Slf4j
public class ZNodeMonitor implements Watcher {

    private ZooKeeper zookeeper;

    private ZookeeperConfig config;

    private Pattern filterPattern = Pattern.compile(".*");

    private ConfigurationAdmin configurationAdmin;
    private Map<String, ZNodeServiceMonitor> serviceMonitors = new HashMap<>();

    public ZNodeMonitor(ConfigurationAdmin configurationAdmin, ZookeeperConfig config) throws IOException, KeeperException, InterruptedException {
        this.config = config;
        this.configurationAdmin = configurationAdmin;
        if (config.getFilter() != null) {
            this.filterPattern = Pattern.compile(config.getFilter());
        }
        initialization();
    }

    public void setConfigurationAdmin(ConfigurationAdmin configurationAdmin) {
        this.configurationAdmin = configurationAdmin;
    }

    public ConfigurationAdmin getConfigurationAdmin() {
        return configurationAdmin;
    }

    public void initialization() throws IOException, KeeperException, InterruptedException {
        this.zookeeper = null;
        this.zookeeper = new ZooKeeper(config.getUrl(), config.getTimeout(), this);
    }

    public void destroy() throws InterruptedException {
        for (String servicePid : serviceMonitors.keySet()) {
            if (filterPattern.matcher(servicePid).matches()) {
                serviceMonitors.get(servicePid).destroy();
            }
        }
        serviceMonitors.clear();
        zookeeper.close();
    }

    @Override
    public void process(WatchedEvent event) {
        Event.KeeperState state = event.getState();
        switch (state) {
            case Disconnected:
                log.info("Disconnected: {}", event);
                break;
        }

        Event.EventType type = event.getType();
        switch (type) {
            case NodeDeleted:
                try {
                    String pid = event.getPath().substring(event.getPath().lastIndexOf("/") + 1);
                    removeServiceMonitor(pid);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }

        try {
            zookeeper.exists(config.getZNode(), this);
            checkForZNode();
        } catch (KeeperException e) {
            log.error("Zookeeper zNode {} exception. Cause: {}", config.getZNode(), e);
            e.printStackTrace();
        } catch (InterruptedException e) {
            log.error("Zookeeper zNode {} exception. Cause: {}", config.getZNode(), e);
            e.printStackTrace();
        }
    }

    private void checkForZNode() throws KeeperException, InterruptedException {
        Set<String> existsService = serviceMonitors.keySet();
        Stat stat = zookeeper.exists(config.getZNode(), this);
        if (stat != null) {
            List<String> nodes = zookeeper.getChildren(config.getZNode(), this);
            for (String node : nodes) {
                if (filterPattern.matcher(node).matches() && !existsService.contains(node)) {
                    addServiceMonitor(node);
                }
            }
        }
    }

    private void addServiceMonitor(String servicePid) throws KeeperException, InterruptedException {
        log.info("install configuration {}", servicePid);
        zookeeper.exists(String.format("%s/%s", config.getZNode(), servicePid), this);

        ZNodeServiceMonitor serviceMonitor = serviceMonitors.get(servicePid);
        if (serviceMonitor == null) {
            serviceMonitor = new ZNodeServiceMonitor(servicePid);
            serviceMonitors.put(servicePid, serviceMonitor);
        }
    }

    private void removeServiceMonitor(String servicePid) throws IOException {
        log.info("remove configuration {}", servicePid);
        Configuration configuration = null;
        if (configurationAdmin != null) {
            configuration = ConfigurationUtils.findConfiguration(configurationAdmin, servicePid);
        }
        if (configuration != null) {
            configuration.delete();
        }

        ZNodeServiceMonitor serviceMonitor = serviceMonitors.get(servicePid);
        if (serviceMonitor != null) {
            serviceMonitor.destroy();
            serviceMonitors.remove(servicePid);
        }
    }

    private class ZNodeServiceMonitor implements Watcher {

        private String servicePid;

        public ZNodeServiceMonitor(String servicePid) throws KeeperException, InterruptedException {
            this.servicePid = servicePid;
            try {
                initialization();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void initialization() throws KeeperException, InterruptedException, IOException {
            if (configurationAdmin == null) {
                return;
            }
            Dictionary<String, String> props = getPropertiesAndWatch();
            Configuration configuration = ConfigurationUtils.findConfiguration(configurationAdmin, servicePid);
            if (configuration == null) {
                if (servicePid.contains("-")) {
                    String factoryPid = servicePid.substring(0, servicePid.lastIndexOf("-"));
                    configuration = configurationAdmin.createFactoryConfiguration(factoryPid, null);
                } else {
                    configuration = configurationAdmin.getConfiguration(servicePid, null);
                }
            }
            if (configuration != null) {
                props.put(ServiceProperties.SERVICE_PID, servicePid);
                configuration.update(props);
            }
        }

        public void destroy() {
        }

        private synchronized Dictionary<String, String> getPropertiesAndWatch()
                throws KeeperException, InterruptedException {
            String path = String.format("%s/%s", config.getZNode(), servicePid);
            Properties properties = new Properties();
            try {
                properties.load(new ByteArrayInputStream(zookeeper.getData(path, ZNodeServiceMonitor.this, null)));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Dictionary<String, String> props = new Hashtable<>();
            for (String propertyName : properties.stringPropertyNames()) {
                String propertyValue = properties.getProperty(propertyName);
                props.put(propertyName.trim(), propertyValue != null ? propertyValue.trim() : null);
            }
            return props;
        }

        @Override
        public void process(WatchedEvent event) {
            Event.EventType type = event.getType();
            switch (type) {
                case NodeDataChanged:
                    try {
                        log.info("update configuration {}", servicePid);
                        Dictionary<String, String> props = getPropertiesAndWatch();
                        Configuration configuration = ConfigurationUtils.findConfiguration(configurationAdmin, servicePid);
                        if (configuration != null) {
                            configuration.update(props);
                        }
                    } catch (KeeperException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;

                case NodeDeleted:
                    break;
            }

            try {
                String path = String.format("%s/%s", config.getZNode(), servicePid);
                zookeeper.exists(path, this);
            } catch (KeeperException e) {
                log.error("Zookeeper zNode {} exception. Cause: {}", config.getZNode(), e);
                e.printStackTrace();
            } catch (InterruptedException e) {
                log.error("Zookeeper zNode {} exception. Cause: {}", config.getZNode(), e);
                e.printStackTrace();
            }
        }
    }
}
