package com.weibangong.message.transfer.model;

import lombok.Data;

import java.util.Date;

/**
 * Created by jianghailong on 16/1/4.
 */
@Data
public class Principal {
    private Long id;

    private Long tenantId;

    private Long createdById;

    private Date createdAt;

    private Long updatedById;

    private Date updatedAt;

    private Integer version;

    private Integer status;

    private String name;

    private String fullname;

    private String avatar;

    private String description;

    private PrincipalType type;

    private Integer userCount;
}
