package com.haizhi.mq.message;

import java.util.List;

/**
 * Created by chenbo on 16/3/30.
 */
public class ViewMessageBody extends MessageBody {
    String sourceTableId;
    String targetTableId;
    List<String> dataIds;
    int op; // 字段值来自 OperationType 常量

    public ViewMessageBody() {
        super();
    }

    public void setSourceTableId(String sourceTableId) {
        this.sourceTableId = sourceTableId;
    }

    public void setTargetTableId(String targetTableId) {
        this.targetTableId = targetTableId;
    }

    public void setDataIds(List<String> dataIds) {
        this.dataIds = dataIds;
    }

    public void setOp(int op) {
        this.op = op;
    }

    public String getSourceTableId() {

        return sourceTableId;
    }

    public String getTargetTableId() {
        return targetTableId;
    }

    public List<String> getDataIds() {
        return dataIds;
    }

    public int getOp() {
        return op;
    }
}
