package com.weibangong.message.transfer.model;

import com.weibangong.mybatis.blueprint.annotations.Encryption;
import com.weibangong.mybatis.blueprint.annotations.Foretaste;
import com.weibangong.mybatis.blueprint.annotations.KeyIndex;
import lombok.Data;

import java.util.Date;

/**
 * Created by jianghailong on 16/1/4.
 */
@Data
public class Message {

    private String uuid;

    private Long id;

    @Foretaste
    private Long tenantId;

    private Long createdById;

    private Long updatedById;

    private Date createdAt;

    private Date updatedAt;

    private Long version;

    @KeyIndex
    private Long sourceId;

    private Integer sourceType;

    private Long targetId;

    private Integer targetType;

    private Integer contentType;

    @Encryption
    private String content;

    public static class ContentType {
        public final static int TEXT = 0;
        public final static int IMAGE = 1;
        public final static int AUDIO = 2;
        public final static int VIDEO = 3;
        public final static int DOC = 4;
        public final static int REPORT = 101;
        public final static int TASK = 103;
        public final static int ANNOUNCEMENT = 104;
        public final static int LEAVE = 201;
        public final static int REVIEW = 404;
        public final static int CARD = 7;
        public final static int POSITION = 8;
        public final static int GROUP_INVITATION = 9;
        public final static int GROUP_APPLICATION = 10;
        public final static int INTELLIGENT_PRESENTATION = 11;
    }
}
