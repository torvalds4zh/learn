package com.weibangong.notification.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by shanshouchen@haizhi.com on 16/3/17.
 *
 * @author shanshouchen
 * @date 16/3/17
 */
@Data
public class Feed<T> {
    protected List<T> items;

    protected Integer total;

    protected Date updatedAt;

    public Feed() {
        this.items = new ArrayList<>();
    }

    public Integer getTotal() {
        if (total != null)
            return total;
        return items == null ? 0 : items.size();
    }
}
