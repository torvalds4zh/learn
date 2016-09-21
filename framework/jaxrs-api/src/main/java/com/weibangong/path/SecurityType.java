package com.weibangong.path;

/**
 * Created by zhangping on 16/3/14.
 */
public enum SecurityType {

    /**
     * 企业
     */
    ORGANIZATION,

    /**
     * 员工
     */
    EMPLOYEE,

    /**
     * 部门
     */
    DEPARTMENT,

    /**
     * 群组
     */
    CHAT_GROUP,

    /**
     * 微秘
     */
    ASSISTANT,

    /**
     * 登录账号 兼容老系统
     */
    PERSON;

    public static SecurityType valueOf(int ordinal) {
        for (SecurityType t : values()) {
            if (t.ordinal() == ordinal) {
                return t;
            }
        }
        return null;
    }

}
