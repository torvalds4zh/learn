package com.weibangong.dept;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangping on 16/2/25.
 */
@Path("/")
@Api(value = "dept", description = "账号－部门")
public interface DeptRestService {

    /**
     * 同步插入历史数据
     *
     * @param paras
     *//*
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)*/
    void batchInsertHistoryDepts(Map<String, Object> paras);

    /**
     * 获取历史数据
     *
     * @param num
     * @return
     */
    @GET
    @Path("/history/{num}")
    @Produces(MediaType.APPLICATION_JSON)
    List<Dept> getHistoryDept(@PathParam("num") Long num);

    /**
     * 同步历史数据
     */
    @GET
    @Path("/sync_old")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    void syncDept();


    /**
     * 按db id 获取
     *
     * @param id
     * @return
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation("按ID获取部门信息")
    Dept getById(@PathParam("id") Long id);

    /**
     * 部门保存
     *
     * @param dept
     * @return
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation("部门信息保存")
    Dept save(Dept dept);

    /**
     * 部门修改 修改名称 修改上级
     * 约定 ：信息修改时传递的修改后的所有信息
     *
     * @param dept
     * @return
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation("部门信息更新")
    Dept update(Dept dept);

    /**
     * 部门删除
     *
     * @param id
     */
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation("按ID删除部门")
    void delete(@PathParam("id") Long id);

    /**
     * 获取公司部门组织树
     *
     * @param tenantId
     * @return
     */
    @GET
    @Path("/tree/{tenantId}")
    @Produces(MediaType.APPLICATION_JSON)
    Dept getDeptTree(@PathParam("tenantId") Long tenantId);

    /**
     * 获取公司部门组织树
     *
     * @return
     */
    @GET
    @Path("/tree")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation("获取部门树")
    Dept getDeptTree();

    /**
     * 获取所有的部门 对内 不需认证的
     *
     * @param tenantId
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<Dept> getAllDepts(@QueryParam("tenantId") Long tenantId);

    /**
     * 获取所有的部门 对外的 需认证的
     *
     * @return
     */
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation("获取所有的部门 平铺结构")
    List<Dept> getAllDepts();

    /**
     * 部门成员变更
     *
     * @param deptMember 变更后的部门所存在的直属成员ID
     */
    @PUT
    @Path("/member")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation("部门成员变更")
    void memberChange(DeptMember deptMember);

    /**
     * 获取部门的所有上下级
     */

    /**
     * 获取部门的所有上下级员工
     */

}
