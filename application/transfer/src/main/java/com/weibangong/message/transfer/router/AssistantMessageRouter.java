package com.weibangong.message.transfer.router;

import com.weibangong.message.transfer.mapper.ChatMapper;
import com.weibangong.message.transfer.mapper.FeedbackMapper;
import com.weibangong.message.transfer.mapper.PrincipalMapper;
import com.weibangong.message.transfer.model.*;
import com.weibangong.message.transfer.service.MsgStreamService;
import com.weibangong.message.transfer.service.SequenceService;
import com.weibangong.message.transfer.util.JacksonJsonUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import java.util.Date;

/**
 * Created by jianghailong on 16/1/4.
 */
@Slf4j
@Data
public class AssistantMessageRouter implements MessageRouter {

    private SequenceService sequenceService;
    private ChatMapper chatMapper;
    private PrincipalMapper principalMapper;
    private FeedbackMapper feedbackMapper;
    private MsgStreamService streamService;

    private boolean robotSwitch ;

    @Override
    public void process(Message message) {
        EventMessage<Message> payload = new EventMessage<>(EventMessage.MessagesEvent.MESSAGES_ADD, message);
        streamService.publish(message.getSourceId(), payload.asText());
        updateSourceChat(message);

        Feedback feedback = new Feedback();
        feedback.setContent(message.getContent());
        feedback.setTenantId(message.getTenantId());
        feedback.setCreatedAt(message.getCreatedAt());
        feedback.setType(2);
        feedback.setCreatedById(message.getSourceId());
        feedbackMapper.insert(feedback);
        log.info("Assistant Message Router !");
        if (robotSwitch) {
            log.info("push to robot targetId is " + message.getSourceId());
//            streamService.robot(JacksonJsonUtil.obj2json(message));
        }
    }

    private void updateSourceChat(Message message) {
        if (chatMapper.selectByPrimaryKey(message.getSourceId(), message.getTargetId()) == null) {
            Chat chat = createChat(message.getSourceId(), message.getTargetId(), Chat.Status.NORMAL);
            EventMessage<Chat> event = new EventMessage<>(EventMessage.ChatsEvent.CHATS_ADD, chat);
            streamService.publish(message.getSourceId(), event.asText());
        }

        Chat record = new Chat();
        //record.setUnread(0); 解决转发给未读状态的chat 未读状态消息 试试水
        record.setStatus(Chat.Status.NORMAL);
        record.setUpdateTime(new Date());
        record.setSourceId(message.getSourceId());
        record.setTargetId(message.getTargetId());
        record.setUpdateTime(new Date());
        record.setVersion(sequenceService.generate(Chat.SYNC_KEY));
        chatMapper.updateByPrimaryKeySelective(record);
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
