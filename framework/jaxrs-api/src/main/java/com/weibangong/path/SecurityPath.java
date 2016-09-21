package com.weibangong.path;

import com.weibangong.security.model.Base;
import lombok.Data;

/**
 * security 物化路径
 *
 * Created by zhangping on 16/3/14.
 */
@Data
public class SecurityPath extends Base {

    /**
     * 物化路径
     */
    private String path;

    /**
     * 数据所属类型
     */
    private int securityType;

    /**
     * 目标id
     */
    private Long ref;

    /**
     * 员工路径来源
     */
    private int pathSource;

    /**
     * 所属ID
     */
    private Long tenantId;
}
