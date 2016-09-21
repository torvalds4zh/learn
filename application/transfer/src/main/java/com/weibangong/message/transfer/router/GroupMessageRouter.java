package com.weibangong.message.transfer.router;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import com.weibangong.message.transfer.model.APNSUtil;
import com.weibangong.message.transfer.model.EventMessage;
import com.weibangong.message.transfer.mapper.ChatMapper;
import com.weibangong.message.transfer.mapper.PrincipalMapper;
import com.weibangong.message.transfer.model.Chat;
import com.weibangong.message.transfer.model.Message;
import com.weibangong.message.transfer.model.Principal;
import com.weibangong.message.transfer.service.MsgStreamService;
import com.weibangong.message.transfer.service.SequenceService;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by jianghailong on 16/1/4.
 */
@Slf4j
@Data
public class GroupMessageRouter implements MessageRouter {

    private ChatMapper chatMapper;
    private PrincipalMapper principalMapper;
    private SequenceService sequenceService;
    private MsgStreamService streamService;

    @Override
    public void process(Message message) {
        if (updateSourceChat(message) == 0) {
            log.error("{} was filtered because the group chat is not existed or deleted! ", message);
            return;
        }
        updateTargetChats(message);

        log.info("Message[sourceId={};tenantId={}]", message.getSourceId(), message.getTenantId());

        EventMessage<Message> event = new EventMessage<>(EventMessage.MessagesEvent.MESSAGES_ADD, message);
        String payload = event.asText();

        Set<Long> userIds = new HashSet<>();
        List<Chat> chats = chatMapper.selectByTargetId(message.getTargetId());

        for (Chat chat : chats) {
            streamService.publish(chat.getSourceId(), payload);

            if (chat.getNotify()) {
                userIds.add(chat.getSourceId());
            } else {
                streamService.distributor("", chat.getSourceId());
            }
        }

        userIds.remove(message.getSourceId());
        if (!userIds.isEmpty()) {
            String alert = generateAlert(message);
            streamService.distributor(alert, userIds, null, "{\"type\":\"0\",\"id\":\"" + message.getTargetId() + "\"}", String.valueOf(message.getTargetId()));
        }
    }

    private int updateSourceChat(Message message) {
        publishChatsUpdate(message.getSourceId(), message.getTargetId());
        Chat record = new Chat();
        //record.setUnread(0); 解决转发给有未读状态人 未读状态消失问题
        record.setStatus(Chat.Status.NORMAL);
        record.setSourceId(message.getSourceId());
        record.setTargetId(message.getTargetId());
        record.setUpdateTime(new Date());
        record.setVersion(sequenceService.generate(Chat.SYNC_KEY));
        return chatMapper.updateByPrimaryKeySelective(record);
    }

    private void updateTargetChats(Message message) {
        publishChatsUpdate(message.getTargetId(), message.getSourceId());
        Chat record = new Chat();
        record.setStatus(Chat.Status.NORMAL);
        record.setSourceId(message.getSourceId());
        record.setTargetId(message.getTargetId());
        record.setUpdateTime(new Date());
        record.setVersion(sequenceService.generate(Chat.SYNC_KEY));
        chatMapper.updateByTargetIdSelectiveAndIncUnread(record);
    }

    private String generateAlert(Message message) {
        Principal source = principalMapper.selectByPrimaryKey(message.getSourceId());
        Principal target = principalMapper.selectByPrimaryKey(message.getTargetId());
        return APNSUtil.generateApnsText4Group(source.getFullname(), target.getFullname(), message, 30);
    }
    //第一次群会话chatsUpdate status
    private void publishChatsUpdate(Long sourceId , Long targetId){
        Chat chat = chatMapper.selectByPrimaryKey(sourceId, targetId);
        if ( chat != null && chat.getStatus()==1) {
            chat.setStatus(Chat.Status.NORMAL);
            chat.setVersion(sequenceService.generate(Chat.SYNC_KEY));
            EventMessage<Chat> event = new EventMessage<>(EventMessage.ChatsEvent.CHATS_UPDATE, chat);
            streamService.publish(sourceId, event.asText());
        }
    }
}
