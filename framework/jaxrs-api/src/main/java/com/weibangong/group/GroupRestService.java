package com.weibangong.group;


import io.swagger.annotations.ApiOperation;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by zhangping on 16/3/22.
 */
@Path("/")
public interface GroupRestService {

    /**
     * 同步群组数据
     */
    @GET
    @Path("/sync_history")
    @Produces(MediaType.APPLICATION_JSON)
    void syncHistory();

    /**
     * 新增群组
     *
     * @param group
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation("新增群组")
    void save(Group group);

    /**
     * 修改群组 包括群名称
     *
     * @param group
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation("修改群组")
    void update(Group group);

    /**
     * 删除群组
     *
     * @param id
     */
    @Path("/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation("删除群组")
    void delete(@PathParam("id") Long id);

    /**
     * 群组加人  可批量
     *
     * @param groupMemberOpt
     */
    @Path("/member/add")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation("群组加入")
    void addMember(GroupMemberOpt groupMemberOpt);

    /**
     * 群组减人  可批量
     *
     * @param groupMemberOpt
     */
    @Path("/member/del")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation("群组踢人")
    void removeMember(GroupMemberOpt groupMemberOpt);

    /**
     * 群组群主 转交
     *
     * @param id
     */
    @Path("/transfer/{id}/{masterId}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation("群组转交")
    void transferGroupMaster(@PathParam("id") Long id, @PathParam("masterId") Long masterId);

    /**
     * @param tenantId
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation("获取公司内所用群组")
    List<Group> getAllGroup(@QueryParam("tenantId") Long tenantId);


}
