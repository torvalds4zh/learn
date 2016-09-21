package com.weibangong.security.model;

/**
 * 状态
 *
 * Created by zhangping on 16/3/2.
 *
 */
public enum Status {

    NEW, ACTIVE, BLOCKED, DELETED, REJECTED;

    public static Status valueOf (int value) {

        for (Status status : Status.values()) {
            if (status.ordinal() == value) {
                return status;
            }
        }

        return Status.ACTIVE;
    }

}
