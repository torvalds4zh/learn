package com.weibangong.device;

import lombok.Data;

import java.util.Date;

/**
 * Created by jianghailong on 15/12/31.
 */
@Data
public class Device {

    private String id;

    private String tenantId;

    private String createdById;

    private Date createdAt;

    private String updatedById;

    private Date updatedAt;

    private Integer version;

    private Integer status;

    /**
     * 设备所有者， 对应用户id
     */
    private String ownerId;

    /**
     * 设备名称， 放弃， 无名字
     */
    private String name;

    /**
     * 操作系统，如android, ios
     */
    private String type;

    /**
     * 系统版本，如7.0.2, 4.2.3
     */
    private String systemVersion;

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 应用版本
     */
    String appVersion;

    /**
     * 默认default:0; appstore:1
     */
    Integer channel;

    /**
     * 设备的token值
     */
    private String devToken;

    /**
     * 英文全称： International Mobile Equipment Identity
     * <p/>
     * 移动设备国际身份码的缩写， 移动设备国际辨别码
     */
    private String imei;

    /**
     * 设备mac地址， 通过X-Requested-Device获取
     */
    private String mac;

    private String accessToken;

    /**
     * 保留用于兼容客户端代码
     */
    public  String dev_uuid;
    /**
     * 保留用于兼容客户端代码
     */

    public void generateId() {
        Long currentSeconds = new Date().getTime() / 1000L;
        this.id = currentSeconds + "" + ownerId;
    }


}
