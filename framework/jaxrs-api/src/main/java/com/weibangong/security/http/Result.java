package com.weibangong.security.http;

import lombok.Data;

/**
 * Created by zhangping on 16/2/17.
 */
@Data
public class Result {

    private String status;

    private String message;

    private Object data;
}
