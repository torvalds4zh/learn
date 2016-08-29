package com.haizhi.mq.test;

import com.google.common.collect.Maps;
import com.haizhi.mq.base.Preconditions;
import com.haizhi.mq.base.PrintEnable;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Map;

/**
 *
 * 消息对象
 *
 * Created by xiaolezheng on 15/8/27.
 */
public class MqMessage extends PrintEnable implements Serializable{

    private static final long serialVersionUID = -6169285512101464319L;
    /**
     * 默认消息属性长度
     */
    private static final int DEFAULT_PROPERTY_SIZE = 10;

    /**
     * 消息主题
     */
    private String topic;

    /**
     * 消息tag, 可以作为二级标题理解; 对rocketMq有用; kafka目前不使用该字段;
     */
    private String tag;

    /**
     * 用作队列路由规则，比如wbg业务公司Id, 或者clientId；key/value对应的value
     * rocketMq用作队列规则; kafka用作分区规则; 都是为了保证消息顺序性,具有同样key的消息有先后顺序;
     */
    private String key;

    /**
     * 消息内容，map格式方便扩展，需要应用端做严格校验，key/value对应的value
     */
    private Map<String, Object> content;

    public static MqMessage create(String topic){
        Preconditions.checkArgument(StringUtils.isNotEmpty(topic), "消息主题不能为空");

        MqMessage mqMessage = new MqMessage();
        Map<String,Object> content = Maps.newHashMapWithExpectedSize(DEFAULT_PROPERTY_SIZE);
        mqMessage.setTopic(topic);
        mqMessage.setContent(content);
        mqMessage.addProperty(MsgConstant.MSG_SYS_TIMESTAMP, System.currentTimeMillis()+"");

        return mqMessage;
    }

    public MqMessage setTopic(String topic){
        this.topic = topic;
        return this;
    }

    public MqMessage setKey(String key){
        Preconditions.checkArgument(StringUtils.isNoneEmpty(key), "分区规则字段key不能为空");

        this.key = key;
        return this;
    }

    public MqMessage addProperty(String key, String value){
        this.content.put(key, value);
        return this;
    }

    public Object getProperty(String key){
        return this.content.get(key);
    }

    public String getTopic() {
        return topic;
    }

    public String getTag() {
        return tag;
    }

    public String getKey() {
        return key;
    }

    public Map<String, Object> getContent() {
        return content;
    }

    public MqMessage setContent(Map<String,Object> content){
        this.content = content;
        return this;
    }

    public MqMessage setTag(String tag) {
        this.tag = tag;
        return this;
    }

    @Override
    public String toString() {
        return "MqMessage{" +
                "topic='" + topic + '\'' +
                ", tag='" + tag + '\'' +
                ", key='" + key + '\'' +
                ", content=" + content +
                '}';
    }
}
