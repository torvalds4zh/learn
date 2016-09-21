package com.weibangong.message.transfer.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by jianghailong on 16/1/4.
 */
public class APNSUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String generateApnsText(String source, Message message, int maxLength) {
        switch (message.getContentType()) {
            case Message.ContentType.IMAGE:
                return source + "：" + APNSText.IMAGE_TEXT;
            case Message.ContentType.DOC:
                return source + "：" + APNSText.DOC_TEXT;
            case Message.ContentType.AUDIO:
                return source + "：" + APNSText.AUDIO_TEXT;
            case Message.ContentType.TEXT:
                String text = deserializeContentField(message, "text");
                text = cutTextLength(text, maxLength);
                return source + "：" + text;
            case Message.ContentType.CARD:
            case Message.ContentType.INTELLIGENT_PRESENTATION:
                return source + "：" + deserializeContentField(message, "title");
            case Message.ContentType.POSITION:
                return source + "：" + APNSText.POSITION_TEXT;
            case Message.ContentType.GROUP_INVITATION:
                return source + "：“" + deserializeContentField(message, "groupName") + "”群组邀请";
            case Message.ContentType.GROUP_APPLICATION:
                return source + "：" + deserializeContentField(message, "applicantName") + deserializeContentField(message, "content");
            default:
                return null;
        }
    }

    public static String generateApnsText4Group(String source, String target, Message message, int maxLength) {
        switch (message.getContentType()) {
            case Message.ContentType.IMAGE:
                return source + "(" + target + ")" + "：" + APNSText.IMAGE_TEXT;
            case Message.ContentType.DOC:
                return source + "(" + target + ")" + "：" + APNSText.DOC_TEXT;
            case Message.ContentType.AUDIO:
                return source + "(" + target + ")" + "：" + APNSText.AUDIO_TEXT;
            case Message.ContentType.TEXT:
                String text = deserializeContentField(message, "text");
                text = cutTextLength(text, maxLength);
                return source + "(" + target + ")" + "：" + text;
            case Message.ContentType.CARD:
                return source + "(" + target + ")" + "：" + deserializeContentField(message, "title");
            case Message.ContentType.POSITION:
                return source + "(" + target + ")" + "：" + APNSText.POSITION_TEXT;
            case Message.ContentType.GROUP_INVITATION:
                return source + "(" + target + ")" + "：“" + deserializeContentField(message, "groupName") + "”群组邀请";
            case Message.ContentType.GROUP_APPLICATION:
                return source + "(" + target + ")" + "：" + deserializeContentField(message, "applicantName") + deserializeContentField(message, "content");
            default:
                return null;
        }
    }


    private static String cutTextLength(String text, int maxLength) {
        if (text.length() > maxLength) {
            text = text.substring(0, maxLength);
            text += "...";
        }

        return text;
    }


    private static String deserializeContentField(Message message, String field) {
        try {
            JsonNode contentNode = objectMapper.readTree(message.getContent());
            return contentNode.get(field).asText();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

}
