package com.weibangong.oplog;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by zhangping on 16/4/22.
 */
@Path("/")
public interface OpLogRestService {


    /**
     * 存储操作日志
     *
     * @param optLog
     */
    @Path("/save")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    void save(OpLog optLog);

    /**
     * 获取操作日志
     *
     * @param tenantId
     */
    @Path("/{tenantId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<OpLog> get(@PathParam("tenantId") Long tenantId);



}
