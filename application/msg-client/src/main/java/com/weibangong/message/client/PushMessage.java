package com.weibangong.message.client;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PushMessage {

    private Map<String, Object> headers;

    private String body;

    private String type;

    private String target;

    public PushMessage () {
        this.headers = new HashMap<>();
        this.body = "";
        this.type = "";
        this.target = "";
    }

    public static PushMessage create() {
        return new PushMessage();
    }

    public PushMessage header(String key, Object value) {
        this.headers.put(key, value);
        return this;
    }

    public PushMessage body(String body) {
        this.body = body;
        return this;
    }

    public PushMessage type(String type) {
        this.type = type;
        return this;
    }

    public PushMessage target(String target) {
        this.target = target;
        return this;
    }

    public PushMessage target(Set<Long> target) {
        StringBuilder builder = new StringBuilder();
        if (target != null && !target.isEmpty()) {
            for (Long userId : target) {
                builder.append(userId).append(",");
            }
            this.target = builder.substring(0, builder.length() - 1);
        }
        return this;
    }

    public void smsPush(PushClient pushClient) {
        pushClient.smsPush(this);
    }

    public void voicePush(PushClient pushClient) {
        pushClient.voicePush(this);
    }

    public void wbgPush(PushClient pushClient) {
        pushClient.wbgPush(this);
    }

    public void secretaryPush(PushClient pushClient) {
        pushClient.secretaryPush(this);
    }


    public String getBody() {
        return body;
    }

    public Map<String, Object> getHeaders() {
        return headers;
    }

    public String getType() {
        return type;
    }

    public String getTarget() {
        return target;
    }
}
