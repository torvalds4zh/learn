package com.weibangong.mybatis.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by haizhi on 15/8/27.
 */
@Data
public class User implements Serializable{
    private Integer id;
    private String name;
    private Date createTime;
    private Date updateTime;
}
