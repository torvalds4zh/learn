package com.weibangong.employee;

import lombok.Data;

import java.util.List;

/**
 * 隐私设置实体
 *
 * Created by zhangping on 16/3/2.
 */
@Data
public class PrivacySetting {

    private List<Long> ids;

    private Privacy privacy;
}
