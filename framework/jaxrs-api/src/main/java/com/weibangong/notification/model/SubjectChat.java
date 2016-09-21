package com.weibangong.notification.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

/**
 * Created by shanshouchen@haizhi.com on 16/3/17.
 *
 * @author shanshouchen
 * @date 16/3/17
 */
@Data
public class SubjectChat implements SyncEntity {

    private Long id;

    @JsonIgnore
    private Long tenantId;

    private String objectId;

    private Integer objectType;

    private String subType;

    @JsonIgnore
    private Long targetId;

    @JsonIgnore
    private Long createdById;

    @JsonIgnore
    private Long createdAt;

    @JsonIgnore
    private Long updatedById;

    @JsonIgnore
    private Long updatedAt;

    @JsonIgnore
    private Long lastSubjectMessageId;

    private SubjectMessage lastSubjectMessage;

    private String title;

    private Long version;

    private Integer status;

    private Integer unread;

    private Integer atFlag;

    private List<Long> idList;
    @JsonIgnore
    private String operation;

    public SubjectMessage createLastSubjectMessage() {
        SubjectMessage message = new SubjectMessage();
        message.setCreatedAt(this.updatedAt);
        message.setCreatedById(this.updatedById);
        message.setOperation(this.operation);
        return message;
    }

    public SubjectChat copy() {
        SubjectChat subjectChat = new SubjectChat();

        subjectChat.setId(this.id);
        subjectChat.setTenantId(this.tenantId);
        subjectChat.setObjectId(this.objectId);
        subjectChat.setObjectType(this.objectType);
        subjectChat.setSubType(this.subType);
        subjectChat.setTargetId(this.targetId);
        subjectChat.setCreatedById(this.createdById);
        subjectChat.setCreatedAt(this.createdAt);
        subjectChat.setUpdatedById(this.updatedById);
        subjectChat.setUpdatedAt(this.updatedAt);
        subjectChat.setLastSubjectMessageId(this.lastSubjectMessageId);
        subjectChat.setTitle(this.title);
        subjectChat.setVersion(this.version);
        subjectChat.setStatus(this.status);
        subjectChat.setUnread(this.unread);
        subjectChat.setAtFlag(this.atFlag);

        return subjectChat;
    }
}
