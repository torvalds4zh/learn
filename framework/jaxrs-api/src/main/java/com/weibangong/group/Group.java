package com.weibangong.group;

import com.weibangong.security.model.Base;
import lombok.Data;

import java.util.List;

/**
 * Created by zhangping on 16/3/22.
 */
@Data
public class Group extends Base {

    /**
     * 公司ID
     */
    private Long tenantId;
    /**
     * 群组头像
     */
    private String avatar;

    /**
     * 群组名
     */
    private String fullname;

    /**
     *  群组所属用户IDS
     */
    private List<Long> userIds;
}
