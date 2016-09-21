package com.weibangong.group;

import lombok.Data;

import java.util.List;

/**
 * Created by zhangping on 16/3/24.
 */
@Data
public class GroupMemberOpt {

    /**
     * 发生变更的群组
     */
    private Long groupId;

    /**
     * 新增或移除的成员
     */
    private List<Long> userIds;
}
