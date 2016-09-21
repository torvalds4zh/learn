package com.weibangong.security.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字段校验
 *
 * Created by zhangping on 16/3/1.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldVaildation {

    String desc() default "";
    /**
     * 是否允许为空
     * @return
     */
    boolean empty() default true;

    /**
     * 长度限制
     *
     * @return
     */
    int length();
}
