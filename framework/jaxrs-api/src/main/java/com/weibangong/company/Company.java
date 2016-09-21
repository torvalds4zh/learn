package com.weibangong.company;

import com.weibangong.security.model.Base;
import lombok.Data;

import java.util.Date;

/**
 * 公司企业信息实体
 *
 * Created by xiaxuan on 16/2/19.
 */
@Data
public class Company extends Base {

    /**
     * 公司简称
     */
    private String name;

    /**
     * 公司全称
     */
    private String fullname;

    /**
     * 公司版本
     */
    private Integer plan;

    /**
     * 公司超管id
     */
    private Long managerId;

    /**
     * 所属灰度环境
     */
    private Integer serverGroup;

    /**
     * 过期时间
     */
    private Date expireTime;
}
