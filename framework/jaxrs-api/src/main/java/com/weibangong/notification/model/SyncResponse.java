package com.weibangong.notification.model;

import lombok.Data;

import java.util.List;

/**
 * Created by shanshouchen@haizhi.com on 16/3/17.
 *
 * @author shanshouchen
 * @date 16/3/17
 */
@Data
public class SyncResponse<T extends SyncEntity> {
    String syncType;

    Long version;

    Boolean includeLastItemInRange;

    List<T> changes;
}
