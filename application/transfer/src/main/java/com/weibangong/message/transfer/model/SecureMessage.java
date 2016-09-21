package com.weibangong.message.transfer.model;

import com.weibangong.mybatis.blueprint.annotations.Encryption;
import com.weibangong.mybatis.blueprint.annotations.KeyIndex;
import lombok.Data;

import java.util.Date;

/**
 * Created by jianghailong on 16/1/8.
 */
@Data
public class SecureMessage {

    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * uuid 用于去重
     */
    private String uuid;

    /**
     * 公司
     */
    @KeyIndex
    private Long tenantId;

    /**
     * 发送者
     */
    private Long sourceId;

    /**
     * 发送者类型
     */
    private Integer sourceType;

    /**
     * 接收者
     */
    private Long targetId;

    /**
     * 接收者类型
     */
    private Integer targetType;

    /**
     * 消息类型
     */
    private Integer contentType;

    /**
     * 消息内容
     */
    @Encryption
    private String content;

    /**
     * 消息创建时间
     */
    private Date createdAt;
}
