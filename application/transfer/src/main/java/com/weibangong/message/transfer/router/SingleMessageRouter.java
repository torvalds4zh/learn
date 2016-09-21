package com.weibangong.message.transfer.router;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weibangong.message.transfer.mapper.ChatMapper;
import com.weibangong.message.transfer.mapper.PrincipalMapper;
import com.weibangong.message.transfer.model.*;
import com.weibangong.message.transfer.service.MsgStreamService;
import com.weibangong.message.transfer.service.SequenceService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * Created by jianghailong on 16/1/4.
 */
@Data
@Slf4j
public class SingleMessageRouter implements MessageRouter {

    private ObjectMapper objectMapper = new ObjectMapper();
    private SequenceService sequenceService;
    private PrincipalMapper principalMapper;
    private ChatMapper chatMapper;
    private MsgStreamService streamService;

    @Override
    public void process(Message message) {
        log.info("Message[sourceId={};tenantId={}]", message.getSourceId(), message.getTenantId());

        updateSourceChat(message);

        Chat targetChat = chatMapper.selectByPrimaryKey(message.getTargetId(), message.getSourceId());

        targetChat = updateTargetChat(message, targetChat);

        EventMessage<Message> payload = new EventMessage<>(EventMessage.MessagesEvent.MESSAGES_ADD, message);
        streamService.publish(message.getTargetId(), payload.asText());
        streamService.publish(message.getSourceId(), payload.asText());

        if (targetChat.getNotify()) {
            String alert = generateAlert(message);
            streamService.distributor(alert, message.getTargetId(), null, "{\"type\":\"0\",\"id\":\"" + message.getSourceId() + "\"}", String.valueOf(message.getSourceId()));
        } else {
            streamService.distributor("", message.getTargetId());
        }
    }

    private void updateSourceChat(Message message) {
        if (chatMapper.selectByPrimaryKey(message.getSourceId(), message.getTargetId()) == null) {
            Chat chat = createChat(message.getSourceId(), message.getTargetId(), Chat.Status.NORMAL);

            EventMessage<Chat> event = new EventMessage<>(EventMessage.ChatsEvent.CHATS_ADD, chat);
            streamService.publish(message.getSourceId(), event.asText());
        }

        Chat record = new Chat();
        //record.setUnread(0);解决转发给有未读状态人 未读状态消失问题
        record.setStatus(Chat.Status.NORMAL);
        record.setUpdateTime(new Date());
        record.setSourceId(message.getSourceId());
        record.setTargetId(message.getTargetId());
        record.setVersion(sequenceService.generate(Chat.SYNC_KEY));
        chatMapper.updateByPrimaryKeySelective(record);
    }

    private Chat updateTargetChat(Message message, Chat targetChat) {
        if (targetChat == null) {
            targetChat = createChat(message.getTargetId(), message.getSourceId(), Chat.Status.NORMAL);

            EventMessage<Chat> event = new EventMessage<>(EventMessage.ChatsEvent.CHATS_ADD, targetChat);
            streamService.publish(message.getTargetId(), event.asText());
        }

        Chat record = new Chat();
        record.setStatus(Chat.Status.NORMAL);
        record.setUpdateTime(new Date());
        record.setSourceId(message.getTargetId());
        record.setTargetId(message.getSourceId());
        record.setVersion(sequenceService.generate(Chat.SYNC_KEY));
        chatMapper.updateByPrimaryKeySelectiveAndIncUnread(record);

        return targetChat;
    }

    private String generateAlert(Message message) {
        Principal principal = principalMapper.selectByPrimaryKey(message.getSourceId());
        return APNSUtil.generateApnsText(principal.getFullname(), message, 30);
    }

    public Chat createChat(Long sourceId, Long targetId, int status) {

        if (sourceId == null || targetId == null || sourceId.equals(0l) || targetId.equals(0l)) {
            throw new IllegalArgumentException("会话创建异常");
        }

        Principal targetPrincipal = principalMapper.selectByPrimaryKey(targetId);

        Chat chat = new Chat();
        chat.setTenantId(targetPrincipal.getTenantId());
        chat.setSourceId(sourceId);
        chat.setTargetId(targetId);
        chat.setTargetType(targetPrincipal.getType().ordinal());
        chat.setPinned(Chat.DefaultValue.PINNED);
        chat.setNotify(Chat.DefaultValue.NOTIFY);
        chat.setBloked(Chat.DefaultValue.BLOCKED);
        chat.setUnread(0);
        chat.setStatus(status);
        chat.setCreatedTime(new Date());
        chat.setVersion(sequenceService.generate(Chat.SYNC_KEY));
        chatMapper.insert(chat);
        log.info("Created chat: " + chat);

        return chat;
    }
}
