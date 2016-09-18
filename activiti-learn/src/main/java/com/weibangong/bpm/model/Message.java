package com.weibangong.bpm.model;

import lombok.Data;

/**
 * Created by chenbo on 16/9/10.
 */
@Data
public class Message {

    private Long userId;

    private Long tenantId;

    private String type;
}
