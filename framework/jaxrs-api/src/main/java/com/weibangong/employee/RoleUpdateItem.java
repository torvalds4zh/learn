package com.weibangong.employee;

import lombok.Data;

import java.util.Set;

/**
 * 用户权限变更model
 *
 * Created by zhangping on 16/4/26.
 */
@Data
public class RoleUpdateItem {

    /**
     * 用户ID
     */
    private Long id;

    /**
     * 用户修改后的权限
     */
    private Set<Role> roles;

}
