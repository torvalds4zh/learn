package com.haizhi.mq.message;

import java.nio.charset.Charset;

public final class MessageConstant {
    /**
     * mqtt -> kafka/rocketMq
     */
    public static final String TOPIC_MESSAGE_ADD = "message_add";

    /**
     * 后台server -> kafka/rocketMq
     */
    public static final String TOPIC_APP_PUSH = "app_push";

    /**
     * dispatcher -> kafka/rocketMq q
     */
    public static final String TOPIC_MQTT_PUSH = "mqtt_push";

    public static final Charset UTF_8 = Charset.forName("UTF-8");


    /**
     * 消息字段名
     */
    public static final String MSG_ID = "msgId";
    public static final String MSG_CONTENT ="content";
    public static final String MSG_UID = "uid";
    public static final String MSG_CLIENT_ID ="clientId";
    public static final String MSG_TENANT_ID = "tenantId";
    public static final String MSG_TTL = "ttl";
    public static final String MSG_TYPE = "type";
    public static final String MSG_RETAINLEN  = "retainLen";
    public static final String MSG_ISDISTINCT ="isDistinct";
    public static final String MSG_SYS_TIMESTAMP = "sys_timestamp";
}