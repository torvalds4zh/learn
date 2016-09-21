package com.weibangong.message.transfer.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.Data;

import java.io.IOException;

/**
 * Created by jianghailong on 16/1/4.
 */
@Data
public class EventMessage<T> {

    private static ObjectMapper objectMapper;

    static {
        JsonFactory jsonFactory = new JsonFactory();
        jsonFactory.enable(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS);

        objectMapper = new ObjectMapper(jsonFactory);
        objectMapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
        objectMapper.enable(SerializationFeature.WRITE_ENUMS_USING_INDEX);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    private String action;

    private T content;

    public EventMessage(String action, T content) {
        this.action = action;
        this.content = content;
    }

    public String asText() {
        try {
            return objectMapper.writeValueAsString(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static class MessagesEvent {
        public static final String MESSAGES_ADD = "messages/add";

        public static final String MESSAGES_ACK = "messages/ack";

        public static final String MESSAGES_CANCEL = "messages/cancel";
    }

    public class ChatsEvent {

        public static final String CHATS_ADD = "chats/add";
        public static final String CHATS_UPDATE = "chats/update";

    }

    public class VchatEvent {

        public static final String VCHAT_CREATED = "vchat/created";

        public static final String VCHAT_INVITED = "vchat/invited";


    }
}
