package com.weibangong.message.transfer.model;

import lombok.Data;

/**
 * Created by jianghailong on 16/1/4.
 */
@Data
public class RequestParameter {

    private String action;

    private Message content;
}
