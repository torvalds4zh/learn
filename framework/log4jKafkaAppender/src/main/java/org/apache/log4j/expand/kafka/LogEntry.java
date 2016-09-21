package org.apache.log4j.expand.kafka;

/**
 * Created by shanshouchen@weibangong.com.
 *
 * @author shanshouchen
 * @date 16/9/2
 */
public class LogEntry {
    private String message;
    private String type;
    private String level;
    private String host;

    public LogEntry(String type, String message) {
        this.type = type;
        this.message = (message == null ? "" : message).replaceAll(" ", "").replaceAll("/", "-").replaceAll("\t", "").replaceAll("\n", "");
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
