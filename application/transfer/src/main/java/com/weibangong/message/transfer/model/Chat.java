package com.weibangong.message.transfer.model;

import lombok.Data;

import java.util.Date;

/**
 * Created by jianghailong on 16/1/4.
 */
@Data
public class Chat {

    public static final String SYNC_KEY = "CHATS_SYNC_KEY";

    private Long sourceId;

    private Long targetId;

    private Long tenantId;

    private Integer targetType;

    private Boolean pinned;// 置顶

    private Boolean notify;// 新消息通知

    private Boolean bloked;// 阻止某人

    private int unread;

    private Integer status;

    private Long version;

    private Date createdTime;

    private Date updateTime;

    public static class DefaultValue {
        /**
         * 置顶开关
         */
        public static final Boolean PINNED = false;
        /**
         * 消息提醒开关
         */
        public static final Boolean NOTIFY = true;
        /**
         * 阻止消息开关
         */
        public static final Boolean BLOCKED = false;
    }

    public static class Status {
        public static final int NORMAL = 0;
        public static final int DELETED = 1;
    }

}
