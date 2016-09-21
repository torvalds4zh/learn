package com.weibangong.message.transfer.model;

import lombok.Data;

import java.util.Date;

@Data
public class Feedback {

    private Long id; // 系统内部ID

    private Long tenantId;  // 租户ID

    private Long createdById;

    private Date createdAt;

    private String content;

    private String appName;

    private String appVersion;

    private String systemName;  // Android, iPhone, iPad, Windows, Mac, Ubuntu, ...

    private String systemVersion;

    private String channel;

    private int type = 0; //0.系统崩溃 1.用户自定义反馈 2.微秘

}
