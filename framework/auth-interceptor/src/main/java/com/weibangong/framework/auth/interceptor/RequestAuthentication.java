package com.weibangong.framework.auth.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestAuthentication {

    /**
     * 表明校验 “以登录账号生成的token” ; 需要校验的接口包括：
     * <list>
     *     /auth/runas   切换公司接口
     *     /auth/companylist  获取公司列表接口
     * </list>
     * @return
     */
    boolean loginToken() default false;

    /**
     * 表明校验 “以登录账号 公司ID 员工ID 生成的token” ; 需要校验所有的接口（不包括 /auth/login; /auth/runas; /auth/companylist ）
     * @return
     */
    boolean token() default true;
}
