package com.weibangong.application;

import lombok.Data;

import java.util.Date;

/**
 * Created by jianghailong on 15/10/28.
 */
@Data
public class KeySecret {

    private Long id;

    /**
     * Length: 16
     * Example: m3Nuft0Fdgl0WKCB
     */
    private String appKey;

    /**
     * Length: 30
     * Example: uXz1R2qXTzALnvSAnyn8Z368i8BOcC
     */
    private String appSecret;

    /**
     * AppKey Secret 状态
     */
    private KeySecretState state;

    /**
     * ref to application id
     */
    private Long appId;

    /**
     * 备注
     */
    private String description;

    /**
     * 创建时间
     */
    private Date createdTime = new Date();
}
