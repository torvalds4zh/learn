package com.weibangong.employee;

/**
 * 用户权限
 * <p/>
 * Created by zhangping on 16/4/25.
 */
public enum Role {

    MEMBER("普通员工", "member"),

    ADMIN("系统管理员", "admin"),

    TREASURER("审批管理员", "treasurer"),

    OPERATOR("人事行政管理员", "operator"),

    ANNOUNCER("公共管理员", "announcer"),

    OUTDOOR("外勤管理员", "outdoor"),

    CRM("CRM管理员", "crm"),

    FINANCE("应用管理员", "finance"),

    ATTENDANCE("考勤管理员", "attendance"),

    EMAIL("邮箱管理员", "email")

    ;

    String name;

    String code;

    Role(String name, String code) {
        this.name = name;
        this.code = code;
    }


}
