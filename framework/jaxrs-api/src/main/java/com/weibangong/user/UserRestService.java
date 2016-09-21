package com.weibangong.user;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Map;

@Path("/")
public interface UserRestService {

    /**
     * 更新用户属性
     * @param userId 用户ID
     * @param properties 属性集
     * @return 成功返回1，失败返回0
     */
    @POST
    @Path("/{userId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Integer register (@PathParam("userId") Long userId, Map<String, Object> properties);

    /**
     * 根据userId返回属性集
     * @param userId 用户ID
     * @return 属性集
     */
    @GET
    @Path("/{userId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Map<String, Object> getUserProperties (@PathParam("userId") Long userId);

    /**
     * 未读数操作（在更新数据库之后操作）
     * @param userId 用户ID
     * @param num 增加数
     * @return 成功返回1，失败返回0
     */
    @POST
    @Path("/{userId}/unread/{num}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Integer unread (@PathParam("userId") Long userId, @PathParam("num") Integer num);

    /**
     * 重置未读数（BUG可能导致缓存与实际数值不一致，故置此方法来处理问题）
     * @param userId 用户ID
     * @return 成功返回1，失败返回0
     */
    @POST
    @Path("/{userId}/unread/reset")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Integer resetUnread (@PathParam("userId") Long userId);
}
