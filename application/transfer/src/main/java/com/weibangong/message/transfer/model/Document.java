package com.weibangong.message.transfer.model;

import lombok.Data;

/**
 * Created by jianghailong on 16/1/4.
 */
@Data
public class Document {

    private Long id;

    private Long tenantId;

    private Long createdById;

    private Long createdAt;

    private Long updatedById;

    private Long updatedAt;

    private Long version;

    private Integer status;

    private String fileMetaData;

    private Long carrierId;

    private Integer carrierType;

    private Long sourceId;

    private Integer sourceType;

    private Long targetId;

    private Integer targetType;

}
