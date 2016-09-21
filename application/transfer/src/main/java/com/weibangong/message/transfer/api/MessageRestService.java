package com.weibangong.message.transfer.api;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by chenbo on 16/9/21.
 */
@Path("/")
public interface MessageRestService {

    /**
     * 添加消息
     * @param param 消息体
     * @return 失败返回失败反馈
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    String messageAdd(String param);

    /**
     * 消息撤回
     * @param param 消息体
     * @return 失败返回失败反馈
     */
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    String messageCancel(String param);
}
