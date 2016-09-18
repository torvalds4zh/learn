package com.haizhi.mq.message;

import com.haizhi.mq.base.PrintEnable;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by chenbo on 16/3/30.
 */
public class MessageBody extends PrintEnable implements Serializable{

    public interface Status {
        int New = 0;
        int Doing = 1; // 处理中
        int Success = 2;
        int Error = 3;
        int Delete = 4;
    }

    private String id;
    private String tenantId;
    private Date createTime;
    private Date updateTime;
    private int status;
    private int costTime; // 消息handler处理时间毫秒
    private String errorMsg;
    private int type;

    public MessageBody() {
        this.status = Status.New;
        this.createTime = new Date();
        this.updateTime = new Date();
        this.costTime = 0;
        this.errorMsg = StringUtils.EMPTY;
    }

    public int getType(){
        return this.type;
    }

    public String getId() {
        return id;
    }

    public String getTenantId() {
        return tenantId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setId(String id) {

        this.id = id;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCostTime() {
        return costTime;
    }

    public void setCostTime(int costTime) {
        this.costTime = costTime;
    }

    public void setType(int type) {
        this.type = type;
    }

}
