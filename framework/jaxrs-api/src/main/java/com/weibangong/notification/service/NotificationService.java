package com.weibangong.notification.service;


import com.weibangong.notification.model.Feed;
import com.weibangong.notification.model.SubjectChat;
import com.weibangong.notification.model.SyncResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

/**
 * 通知中心
 * Created by shanshouchen@haizhi.com on 16/3/17.
 *
 * @author shanshouchen
 * @date 16/3/17
 */

@Path("subject/chats")
public interface NotificationService {

    /**
     * 全标记已读
     *
     * @return
     */
    @PUT
    @Path("/markallread")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    Integer markReadAll();

    /**
     * 清除所有已读通知
     *
     * @return
     */
    @PUT
    @Path("/clearallread")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    Integer clearAllRead();

    /**
     * 批量修改通知状态
     *
     * @param subjectChat
     * @return
     */
    @PUT
    @Path("/batch")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    Integer batchUpdate(SubjectChat subjectChat);

    /**
     * 修改通知状态（for Web）
     *
     * @param subjectChat
     * @return
     */
    @PUT
    @Path("/like")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    Integer updateLike(SubjectChat subjectChat);

    /**
     * 修改通知状态
     *
     * @param id
     * @param subjectChat
     * @return
     */
    @PUT
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    Integer update(@PathParam("id") Long id, SubjectChat subjectChat);

    /**
     * 获取通知中心未读列表
     *
     * @param userId
     * @param type
     * @return
     */
    @GET
    @Path("/unread")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    Map<Integer, List<SubjectChat>> getUnreadSubjects(@QueryParam("userId") Long userId, @QueryParam("type") Integer type);

    /**
     * 按照类别获取通知中心列表
     *
     * @param userId
     * @param types
     * @param offset
     * @param limit
     * @return
     */
    @GET
    @Path("/types")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    Map<String, Object> getSubjectsByTypes(@QueryParam("userId") Long userId, @QueryParam("types") List<Integer> types, @QueryParam("offset") Integer offset, @QueryParam("limit") Integer limit);


    /**
     * 获取已读通知中心列表, for web
     *
     * @param userId
     * @param offset
     * @param limit
     * @return
     */
    @GET
    @Path("/history")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    Feed<SubjectChat> getHistorySubjects(@QueryParam("userId") Long userId, @QueryParam("offset") Integer offset, @QueryParam("limit") Integer limit);

    /**
     * 获取通知中心列表(Web端从1.5.0开始弃用该接口)
     *
     * @param userId
     * @param offset
     * @param limit
     * @return
     */
    @Deprecated
    @GET
    @Path("/list")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    Feed<SubjectChat> getSubjectChatList(@QueryParam("userId") Long userId, @QueryParam("offset") Integer offset, @QueryParam("limit") Integer limit);

    /**
     * 同步最新通知 for app
     *
     * @param userId
     * @param version
     * @param maxChangesReturned
     * @return
     */
    @GET
    @Path("/sync")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    SyncResponse<SubjectChat> sync(@QueryParam("userId") Long userId, @QueryParam("version") Long version,
                                   @QueryParam("maxChangesReturned") Integer maxChangesReturned);
}

