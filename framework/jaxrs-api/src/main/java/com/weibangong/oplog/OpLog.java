package com.weibangong.oplog;

import lombok.Data;

import java.util.Date;

/**
 * Created by zhangping on 16/4/22.
 */
@Data
public class OpLog {

    private Date date;

    private Long tenantId;

    private Long optId;

    private String optContent;

}
