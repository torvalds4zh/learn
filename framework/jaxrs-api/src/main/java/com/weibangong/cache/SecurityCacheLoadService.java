package com.weibangong.cache;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangping on 16/4/6.
 */
public interface SecurityCacheLoadService {


    /**
     * TODO  load path
     *
     * @param tenantId
     */
    @GET
    @Path("/load")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    void execute(@QueryParam("tid") Long tenantId);

    /**
     * 存储账号的基本信息 与老系统保持一致
     *
     * @param userMetaVo
     */
    @POST
    @Path("/usermeta")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    void set(UserMetaVo userMetaVo);


    @POST
    @Path("/usermeta/batch")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    void set(List<UserMetaVo> userMetaVos);

    /**
     * 存储业务上的数据 譬如验证码等
     *
     * @param key
     * @param value
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    void set(String key, String value);

    /**
     * 存储业务上的数据 譬如验证码等
     *
     * @param key
     */
    @POST
    @Path("/map")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    void set(String key, Map<String, String> values);


    @GET
    @Path("/usermeta/{tenantId}/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    UserMetaVo get(@PathParam("id") Long id, @PathParam("tenantId") Long tenantId);

    @GET
    @Path("/usermeta/batch")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)

    List<UserMetaVo> get(BatchUserMetaVo batchUserMetaVo);

    /**
     * 获取业务上的数据 譬如验证码等
     */
    String get(String key);

    Long generate(String key, final long integer);

}
