package com.weibangong.message.client;

public enum HintTemplate {
    GROUP_CHAT_ADD("{executor}邀请{accepter}加入了群聊"),

    GROUP_CHAT_TICK("{executor}将{accepter}移出了群聊"),

    GROUP_CHAT_LEAVE("{executor}退出了群聊"),

    GROUP_NAME_CHANGE("{executor}修改群名“{accepter}”");

    private String action;

    HintTemplate(String action) {
        this.action = action;
    }

    public String getAction() {
        return this.action;
    }
}
