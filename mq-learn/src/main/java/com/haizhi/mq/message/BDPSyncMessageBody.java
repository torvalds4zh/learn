package com.haizhi.mq.message;

/**
 * Created by chenbo on 16/3/30.
 */
public class BDPSyncMessageBody extends MessageBody{
    public static final Integer TABLE_CHANGE = 0;

    public static final Integer DATA_CHANGE = 1;


    private String formId;

    private Integer syncType;

    public BDPSyncMessageBody() {
        super();
        setType(MessageType.BdpSync);
    }

    public BDPSyncMessageBody(String tenantId, String formId, Integer syncType) {
        this();
        this.formId = formId;
        this.syncType = syncType;
        setTenantId(tenantId);
        setStatus(Status.New);
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public Integer getSyncType() {
        return syncType;
    }

    public void setSyncType(Integer syncType) {
        this.syncType = syncType;
    }
}
