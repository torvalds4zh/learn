package com.weibangong.notification.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * Created by shanshouchen@haizhi.com on 16/3/17.
 *
 * @author shanshouchen
 * @date 16/3/17
 */
@Data
public class SubjectMessage {
    @JsonIgnore
    private Long id;

    @JsonIgnore
    private Long tenantId;

    private Long createdById;

    private Long createdAt;

    @JsonIgnore
    private Long updatedById;

    @JsonIgnore
    private Long updatedAt;

    @JsonIgnore
    private Long version;

    @JsonIgnore
    private Integer status;

    @JsonIgnore
    private String objectId;

    @JsonIgnore
    private Integer objectType;

    @JsonIgnore
    private String subType;

    private String operation;

    @JsonIgnore
    private String content;
}
