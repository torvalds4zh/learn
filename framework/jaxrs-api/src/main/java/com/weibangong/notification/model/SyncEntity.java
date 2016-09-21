package com.weibangong.notification.model;

/**
 * Created by shanshouchen@haizhi.com on 16/3/17.
 *
 * @author shanshouchen
 * @date 16/3/17
 */
public interface SyncEntity {
    Long getVersion();

    void setVersion(Long version);
}
