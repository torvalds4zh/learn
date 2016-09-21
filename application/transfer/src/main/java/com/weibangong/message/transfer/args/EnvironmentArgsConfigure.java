package com.weibangong.message.transfer.args;

import lombok.Data;

/**
 * Created by HaiZhi on 20/7/16.
 */
@Data
public class EnvironmentArgsConfigure {
    private String encryptSwitch = "off";
    private String encryptTenants;
    private String wbgToken;
    public void init() {
        System.setProperty("wbg.token", wbgToken);
        System.setProperty("com.weibangong.mybatis.blueprint.interceptor.encrypt.switch", encryptSwitch);
        System.setProperty("com.weibangong.mybatis.blueprint.interceptor.encrypt.tenants", encryptTenants);
    }
}
