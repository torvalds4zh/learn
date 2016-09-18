package com.haizhi.mq.message;

import java.util.List;
import java.util.Map;

/**
 * Created by chenbo on 16/3/30.
 */
public class FormTriggerMessageBody extends MessageBody{

    private String triggerId;
    private String formId;
    private int triggerType;
    private int triggerEvent;
    private int triggerOperation;
    private String name;
//    private List<FormData> data;
    private Map<String, Object> params;

    public FormTriggerMessageBody(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTriggerId(String triggerId) {
        this.triggerId = triggerId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public void setTriggerEvent(int triggerEvent) {
        this.triggerEvent = triggerEvent;
    }

    public void setTriggerOperation(int triggerOperation) {
        this.triggerOperation = triggerOperation;
    }

//    public void setData(List<FormData> data) {
//        this.data = data;
//    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public String getTriggerId() {

        return triggerId;
    }

    public String getFormId() {
        return formId;
    }

    public int getTriggerEvent() {
        return triggerEvent;
    }

    public int getTriggerOperation() {
        return triggerOperation;
    }

//    public List<FormData> getData() {
//        return data;
//    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setTriggerType(int triggerType) {
        this.triggerType = triggerType;
    }

    public int getTriggerType() {

        return triggerType;
    }
}
