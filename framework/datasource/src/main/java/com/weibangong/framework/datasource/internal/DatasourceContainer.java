package com.weibangong.framework.datasource.internal;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbcp2.BasicDataSource;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by jianghailong on 15/12/12.
 */
@Slf4j
public class DatasourceContainer {

    private static final String INSTANCE = "instance";

    private BundleContext bundleContext;
    private final Object lock = new Object();
    private Map<String, BasicDataSource> dataSourceContainer = new HashMap<>();
    private Map<String, ServiceRegistration<DataSource>> serviceRegistrationContainer = new HashMap<>();

    private Properties defaultProperties = new Properties();

    /**
     * db.maxIdle=10
     * db.minIdle=3
     * db.initialSize=5
     * db.maxWaitMillis=10000
     * db.testOnCreate=true
     * db.testOnBorrow=false
     * db.testOnReturn=true
     * db.testWhileIdle=true
     * db.timeBetweenEvictionRunsMillis=60000
     * db.poolPreparedStatements=true
     * db.validationQuery=SELECT NOW() FROM DUAL
     * db.defaultAutoCommit=true
     *
     * db.logAbandoned=true
     * db.removeAbandoned=true
     * db.numTestsPerEvictionRun=10
     * db.minEvictableIdleTimeMillis=10000
     * db.maxConnLifetimeMillis=600000
     * db.removeAbandonedTimeout=300
     */
    public DatasourceContainer(BundleContext bundleContext) {
        this.bundleContext = bundleContext;

        defaultProperties.put("maxIdle", "10");
        defaultProperties.put("minIdle", "3");
        defaultProperties.put("initialSize", "5");
        defaultProperties.put("maxWaitMillis", "10000");
        defaultProperties.put("testOnCreate", "true");
        defaultProperties.put("testOnBorrow", "false");
        defaultProperties.put("testOnReturn", "true");
        defaultProperties.put("testWhileIdle", "true");
        defaultProperties.put("timeBetweenEvictionRunsMillis", "60000");
        defaultProperties.put("poolPreparedStatements", "true");
        defaultProperties.put("validationQuery", "SELECT NOW() FROM DUAL");
        defaultProperties.put("defaultAutoCommit", "true");

        defaultProperties.put("logAbandoned", "true");
        defaultProperties.put("removeAbandoned", "true");
        defaultProperties.put("numTestsPerEvictionRun", "10");
        defaultProperties.put("minEvictableIdleTimeMillis", "10000");
        defaultProperties.put("maxConnLifetimeMillis", "600000");
        defaultProperties.put("removeAbandonedTimeout", "300");
    }

    public void applyConfigure(String pid, Dictionary<String, ?> dictionary) throws Exception {
        synchronized (lock) {
            doApplyConfigure(pid, dictionary);
        }
    }

    private void doApplyConfigure(String pid, Dictionary<String, ?> dictionary) throws Exception {
        Properties properties = new Properties();
        for (Object propertyKey : defaultProperties.keySet()) {
            properties.put(propertyKey, defaultProperties.get(propertyKey));
        }

        Enumeration<String> iterator = dictionary.keys();
        while (iterator.hasMoreElements()) {
            String key = iterator.nextElement();
            if (key.startsWith("db.")) {
                properties.put(key.substring(3), dictionary.get(key));
            }
        }

        if (dataSourceContainer.get(pid) != null) {
            return;
        }

        BasicDataSource dataSource = BundleClassLoaderDataSourceFactory.createDataSource(properties);
        BasicDataSource ds = dataSourceContainer.get(pid);
        dataSourceContainer.put(pid, dataSource);

        Dictionary<String, Object> props = new Hashtable<>();
        if (dictionary.get(INSTANCE) != null) {
            Object instance = dictionary.get(INSTANCE);
            props.put(INSTANCE, instance);
            log.info("init datasource (pid={}, instance={})", pid, instance);
        }
        serviceRegistrationContainer.put(pid, bundleContext.registerService(DataSource.class, dataSource, props));
        if (ds != null) {
            if (!ds.isClosed()) {
                ds.close();
            }
        }
    }

    public void removeConfigure(String pid) {
        synchronized (lock) {
            try {
                doRemoveConfigure(pid);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void doRemoveConfigure(String pid) throws SQLException {
        BasicDataSource datasource = dataSourceContainer.get(pid);
        if (datasource != null) {
            datasource.close();
        }

        ServiceRegistration<DataSource> sr = serviceRegistrationContainer.get(pid);
        if (sr != null) {
            sr.unregister();
        }
    }
}
