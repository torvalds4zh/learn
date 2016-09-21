package com.weibangong.framework.configadmin.internal;

import lombok.Data;

/**
 * Created by jianghailong on 15/11/19.
 */
@Data
public class ZookeeperConfig {

    public static final String GLOBAL_CONFIG_NODE="global-config";

    private String url;

    private int timeout;

    private String zNode;

    private String filter;

    public ZookeeperConfig() {}

    public ZookeeperConfig(String url, int timeout, String zNode, String filter) {
        this.url = url;
        this.timeout = timeout;
        this.zNode = zNode;
        this.filter = filter;
    }
}
