package com.weibangong.cache;

import lombok.Data;

import java.util.List;

/**
 * Created by zhangping on 16/5/9.
 */
@Data
public class BatchUserMetaVo {

    private List<Long> ids;

    private Long tenantId;
}
