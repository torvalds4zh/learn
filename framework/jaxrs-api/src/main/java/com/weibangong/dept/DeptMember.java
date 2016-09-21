package com.weibangong.dept;

import lombok.Data;

import java.util.List;

/**
 * 部门成员变更
 *
 * Created by zhangping on 16/3/16.
 */
@Data
public class DeptMember {

    /**
     * 部门id
     */
    private Long id;

    /**
     * 部门成员IDS
     *
     */
    private List<Long> memberIds;
}
