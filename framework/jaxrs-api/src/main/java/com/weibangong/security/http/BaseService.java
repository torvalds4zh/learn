package com.weibangong.security.http;

import com.weibangong.security.model.Base;
import com.weibangong.security.model.Status;

import java.util.Date;

/**
 * Created by zhangping on 16/3/8.
 */
public class BaseService<T extends Base> {

    public void init (T t) {
        t.setStatus(Status.ACTIVE.ordinal());

        Date now = new Date();

        t.setUpdatedAt(now);
        t.setCreatedAt(now);
    }
}
