package com.weibangong.cache;

/**
 * Created by zhangping on 16/5/7.
 */
public enum IdGeneratorType {

    IDENTIFIER_WORK(1, "IDENTIFIER_SECURITY_SYNC_KEY");

    private final int type;

    private final String syncKey;

    IdGeneratorType(int type, String syncKey) {
        this.type = type;
        this.syncKey = syncKey;
    }

    public int getType() {
        return this.type;
    }

    public String getSyncKey() {
        return this.syncKey;
    }

    public static IdGeneratorType getByType(Integer type) {
        if (type == null) {
            return null;
        }
        for (IdGeneratorType idGenerator : values()) {
            if (idGenerator.getType() == type) {
                return idGenerator;
            }
        }
        return null;
    }
}
