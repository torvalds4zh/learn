package com.weibangong.employee;

import lombok.Data;

import java.util.Date;

/**
 * Created by xiaxuan on 16/4/8.
 */
@Data
public class BlackList {

    /**
     * 黑名单id，为部门id，或者人id
     */
    private Long id;

    /**
     * 黑名单名字
     */
    private String name;

    /**
     * 黑名单所在部门
     */
    private String dept;

    /**
     * 是否是部门
     */
    private Integer isDept;

    /**
     * 时间
     */
    private Date time;

    /**
     * 公司id
     */
    private Long tenantId;

    public enum ISDEPT {

        /**
         * 不是部门
         */
        NOT,

        /**
         * 是部门
         */
        IS
    }

}
