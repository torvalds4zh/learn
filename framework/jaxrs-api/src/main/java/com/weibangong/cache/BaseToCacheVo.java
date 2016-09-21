package com.weibangong.cache;

import lombok.Data;

import java.util.List;

/**
 * Created by zhangping on 16/5/9.
 */
@Data
public class BaseToCacheVo {

    /**
     * 待更新缓存的对象id
     */
    private List<Long> ids;
}
