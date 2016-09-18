package com.weibangong.bpm.config;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.ClassRelativeResourceLoader;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;

public class ApplicationConfigurer extends PropertyPlaceholderConfigurer {

    private static final String HAIZHI_ENV = "haizhi.config.env";

    private String baseLocation;
    private String env;
    private String[] configs;

    protected void loadProperties(Properties props) throws IOException {
        for (int i = 0; i < configs.length; i++) {
            Properties properties = loadPropertiesWithUrl(configs[i]);
            props.putAll(properties);
        }
    }

    private Properties loadPropertiesWithUrl(String cfg) throws IOException {
        String url = getUrl(cfg);
        Properties properties;
        if (url.startsWith("classpath:/")) {
            properties = loadFromClasspath(url);
        } else if (url.startsWith("file:/")) {
            properties = loadFromFileSystem(url);
        } else if (url.startsWith("http://")) {
            properties = loadFromHttp(url);
        } else if (url.startsWith("zk://")) {
            //TODO add zk support later
            properties = new Properties();
        } else {
            throw new IOException("configuration was not found, baseLocation:" + baseLocation + ", env:" + env + ", cfg:" + cfg);
        }
        return parseProperties(properties, cfg);
    }

    private Properties loadFromClasspath(String path) throws FileNotFoundException, IOException {
        String tmpPath = path.substring("classpath:".length(), path.length());
        ClassRelativeResourceLoader loader = new ClassRelativeResourceLoader(ApplicationConfigurer.class);
        Resource res = loader.getResource(tmpPath);
        InputStream inputStream = res.getInputStream();
        Properties properties = new Properties();
        properties.load(inputStream);
        if (inputStream != null) {
            inputStream.close();
        }
        return properties;
    }

    private String getUrl(String cfg) {
        StringBuilder sb = new StringBuilder();
        sb.append(this.baseLocation);
        if (!this.baseLocation.endsWith("/")) {
            sb.append("/");
        }
        sb.append(getEnv());
        sb.append("/");
        sb.append(cfg);
        return sb.toString();
    }

    private Properties parseProperties(Properties properties, String cfg) throws IOException {
        int index = cfg.lastIndexOf(".");
        if (index == -1) {
            index = cfg.length() - 1;
        }
        String prefix = cfg.substring(0, index);
        Enumeration enu = properties.keys();
        Properties temp = new Properties();
        while (enu.hasMoreElements()) {
            String name = enu.nextElement().toString();
            if ("include".equalsIgnoreCase(name)) {
                temp.putAll(loadPropertiesWithUrl(properties.getProperty(name)));
            } else {
                temp.put(prefix + "." + name, properties.getProperty(name));
            }
        }
        return temp;
    }

    private Properties loadFromFileSystem(String path) throws FileNotFoundException, IOException {
        FileSystemResourceLoader loader = new FileSystemResourceLoader();
        Resource res = loader.getResource(path);
        InputStream inputStream = res.getInputStream();
        Properties properties = new Properties();
        properties.load(inputStream);
        if (inputStream != null) {
            inputStream.close();
        }
        return properties;
    }

    private Properties loadFromHttp(String sUrl) throws IOException {
        URL url = new URL(sUrl);
        Properties properties = new Properties();
        InputStream inputStream = url.openStream();
        properties.load(inputStream);
        if (inputStream != null) {
            inputStream.close();
        }
        return properties;
    }

    public void setBaseLocation(String baseLocation) {
        this.baseLocation = baseLocation;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getEnv() {
        String tmp_env = System.getProperty(HAIZHI_ENV);
        if (tmp_env != null && !"null".equals(tmp_env) && !"".equals(tmp_env)) {
            return tmp_env;
        } else if (this.env != null) {
            return this.env;
        } else {
            return "dev";
        }
    }

    public void setConfigs(String[] configs) {
        this.configs = configs;
    }
}