package com.weibangong.application;

import lombok.Data;

import java.util.Date;

/**
 * Created by jianghailong on 15/10/28.
 */
@Data
public class Application {

    private Long id;

    /**
     * 应用名称
     */
    private String name;

    /**
     * 应用状态
     */
    private ApplicationState state;

    /**
     * 应用描述
     */
    private String description;

    /**
     * 创建时间
     */
    private Date createdTime;
}
