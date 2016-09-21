package com.weibangong.security.exception;

/**
 * Created by zhangping on 16/2/17.
 */
public enum SecurityErrorMsg {

    SECURITY_ERROR_001("您的账号或密码错误!"),

    SECURITY_TOKEN_INVALID ("Token无效"),

    SECURITY_TOKEN_ANALYSIS_ERROR("token令牌解析失败!"),

    SECURITY_ERROR_002("您当前未加入任何公司!"),

    SECURITY_ERROR_003("账号待审核!"),

    SECURITY_ERROR_004("账号已停用!"),

    SECURITY_ERROR_005("账号不存在!"),

    /**
     * *** 字段信息校验 异常信息         ******
     */

    FIELD_VALIDATION_EMPTY("{0}不允许为空!"),

    FIELD_VALIDATION_LENGTH("{0}长度不允许超过{1}位!"),


    /**
     * *** account 登录账号 相关异常信息 ******
     */

    ACCOUNT_PASSWORD_NOT_MATCH("原密码不匹配."),

    /**
     * company 企业信息 异常
     */
    COMPANY_NAME_EXIST ("企业简称不允许重复!"),

    /**
     * employe **
     */
    EMPLOYEE_MOBILE_EXIST("联系方式{0}已存在!"),

    EMPLOYEE_SN_EXIST("员工编号{0}已存在!"),

    EMPLOYEE_PERMISSION_DENIED("当前用户无权进行此操作。"),

    ADMIN_CAN_ONLY_BE_MANAGED_BY_SUPER_ADMIN("只有超级管理员可以添加和删除系统管理员。"),

    SUPER_ADMIN_ROLE_CAN_NOT_BE_UPDATED("你是公司系统管理员，不能取消该身份。"),

    /**
     * dept
     */
    DEPT_FULLNAME_EXIST("部门{0}已存在"),

    /**
     * group
     */
    GROUP_CREATED_ERROR("无权修改当前群组的群主"),


    /**
     * cache
     */
    IMG_CODE_ERROR("图片验证码错误, 请重新输入!"),

    VERIFICATION_CODE_NOT_MATCH("验证码错误, 请重新输入!"),

    ;




    private final String code;

    private final String message;

    private static final int OFFSET = 10000;

    SecurityErrorMsg(String message) {
        this.code = Integer.toString(OFFSET + ordinal());
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
