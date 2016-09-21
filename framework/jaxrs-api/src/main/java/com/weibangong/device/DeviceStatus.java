package com.weibangong.device;

/**
 * Created by shanshouchen@haizhi.com on 16/2/23.
 *
 * @author shanshouchen
 * @date 16/2/23
 */
public interface DeviceStatus {
    int OFFLINE = 0; // 新登录，未上线

    int ONLINE = 1;   // 已上线

    int BLOCKED = 2;   // 已禁用

    int DELETE = 3;   //  已删除

    int EXPIRED = 4;   // 已失效
}
