package com.weibangong.message.transfer.model;

/**
 * Created by jianghailong on 16/1/4.
 */
public enum PrincipalType {
    SYSTEM,                     // 0 - 系统

    GROUP,                      // 1 - 群组

    USER,                       // 2 - 个人

    REPORT(true, "汇报中心"),

    REVIEW(true, "审批中心"),

    ANNOUNCEMENT(true, "公告中心"),

    TASK(true, "任务中心"),

    ASSISTANT(true, "微秘"),

    AUTHORITY,

    PROCESS,

    EMAIL_REMINDER(true, "邮件提醒"),

    ORG,                        // 11 - 部门

    TENANT,                     // 12 - 租户

    PERSON;                         // 13 个人

    /**
     * 虚拟用户，企业内唯一
     */
    private final boolean virtual;

    private final String fullname;

    private PrincipalType() {
        this.virtual = false;
        this.fullname = "";
    }

    private PrincipalType(boolean virtual, String fullname) {
        this.virtual = virtual;
        this.fullname = fullname;
    }

    public boolean isVirtual() {
        return virtual;
    }

    public String getFullname() {
        return fullname;
    }
}
