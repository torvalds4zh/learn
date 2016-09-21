package com.weibangong.mybatis.blueprint.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by jianghailong on 16/1/9.
 */
@Data
@NoArgsConstructor
public class SecureKey implements Serializable {

    private Long id;

    private Long keyIndex;

    private String action;

    private String secretKey;

    private Date createdAt;

    public SecureKey(Long keyIndex, String action, String secretKey) {
        this.keyIndex = keyIndex;
        this.action = action;
        this.secretKey = secretKey;
    }

    public SecureKey(Long keyIndex, String secretKey) {
        this.keyIndex = keyIndex;
        this.secretKey = secretKey;
    }
}
