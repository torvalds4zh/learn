package com.weibangong.message.transfer.model;

import lombok.Data;

/**
 * 通用参数类，用来适配所有参数形式，不固定，可任意增添
 */
@Data
public class CommonParams {

    private Long userId;

    private Long sourceId;

    private Long targetId;

    private Long version;

    private Long id;

    private Integer limit;

    private String warn;

    private String deviceId;

    public static class WarnTemplate {
        public final static String TIME_OUT = "超过5分钟，消息无法撤回。";
    }

}
