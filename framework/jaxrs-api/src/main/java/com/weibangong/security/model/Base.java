package com.weibangong.security.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zhangping on 16/3/1.
 *
 * 基本属性
 */
@Data
public class Base implements Serializable{

    /**
     * id
     */
    private Long id;

    /**
     * 状态
     */
    private int status;

    /**
     * 创建者id
     */
    private Long createdById;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 更新者id
     */
    private Long updatedById;

    /**
     * 更新时间
     */
    private Date updatedAt;

    /**
     * 公司id
     */
    private Long tenantId;
}
