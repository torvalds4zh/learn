package com.haizhi.mq.base;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 可打印对象
 *
 * Created by xiaolezheng on 15/8/27.
 */
public class PrintEnable {
    public String toString(){
         return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
