package com.weibangong.path;

import lombok.Data;

import java.util.List;

/**
 * Created by zhangping on 16/5/6.
 */
@Data
public class DeptMemberVo {

    private Long tenantId;

    private Long id;

    private List<Long> addMemberIds;

    private List<Long> removeMemberPathIds;
}
