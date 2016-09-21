package com.weibangong.security.annotation;

/**
 * 操作日志
 * Created by zhangping on 16/3/31.
 */
public @interface OpLogOption {

    /**
     * 操作描述
     *
     * @return
     */
    String opt() default "";

    /**
     * 操作发生变更的字段
     *
     * @return
     */
    String optKey() default "";

    OpAction action ();

    Class<?> clazz();

    enum OpAction {
        SAVE, UPDATE, DELETE
    }
}
