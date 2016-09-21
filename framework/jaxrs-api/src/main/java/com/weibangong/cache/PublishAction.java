package com.weibangong.cache;

import lombok.Data;

/**
 * redis cache 发布动作
 *
 * Created by zhangping on 16/3/26.
 */
@Data
public class PublishAction {

    private Action action;

    private String key;

    private UserMetaVo userMetaVO;

    public enum Action {
        ADD, UPDATE, REMOVE
    }
}
