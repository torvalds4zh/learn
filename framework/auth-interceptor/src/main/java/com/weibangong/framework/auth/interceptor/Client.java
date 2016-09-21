package com.weibangong.framework.auth.interceptor;

import lombok.Data;

@Data
public class Client {

    private Long loginId;

    private Long runasId;

    private Long tenantId;

    private Long clientId;

    private String appName;

    private String appVersion;

    private SystemType systemType;

    private String SystemVersion;

    private int channel;

    public enum SystemType {

        /**
         * WEB端
         */
        WEB,

        /**
         * ios
         */
        IOS,

        /**
         * android
         */
        ANDROID

    }
}
