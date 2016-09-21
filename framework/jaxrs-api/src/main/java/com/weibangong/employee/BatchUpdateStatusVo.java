package com.weibangong.employee;

import lombok.Data;
import com.weibangong.security.model.Status;

import java.util.List;

/**
 * 批量更新员工状态VO
 *
 * Created by zhangping on 16/4/25.
 */
@Data
public class BatchUpdateStatusVo {

    private List<Long> ids;

    private Status status;
}
