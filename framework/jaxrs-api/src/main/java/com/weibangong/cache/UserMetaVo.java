package com.weibangong.cache;

import com.weibangong.path.SecurityType;
import lombok.Data;
import com.weibangong.security.model.Status;

import java.io.Serializable;

/**
 * Created by zhangping on 16/3/29.
 */
@Data
public class UserMetaVo implements Serializable{

    private static final Long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    /**
     * tenantId
     */
    private Long tenantId;

    /**
     * 名称
     */
    private String fullname;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 状态
     */
    private Status status;

    /**
     * 权限
     */
    private Long access;

    /**
     * 类别
     */
    private SecurityType type;

}
