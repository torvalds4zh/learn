package com.weibangong.account;

import io.swagger.annotations.ApiOperation;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangping on 16/2/25.
 */
@Path("/")
public interface AccountRestService {


    /**
     * 按db id 获取
     *
     * @param id
     * @return
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation("按ID获取登录账号")
    Account getById(@PathParam("id") Long id);

    /**
     * 按手机号查询登录账号
     *
     * @param mobile
     * @return
     */
    @GET
    @Path("/mobile/{mobile}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation("按手机号获取登录账号")
    Account getByMobile(@PathParam("mobile") String mobile);


    /**
     * 同步插入历史数据
     *
     * @param paras
     */
    @POST
    @Path("/sync")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    void batchInsertHistoryAccounts(Map<String, Object> paras);

    /**
     * 获取历史数据
     *
     * @param num
     * @return
     */
    @GET
    @Path("/history/{num}")
    @Produces(MediaType.APPLICATION_JSON)
    List<Account> getHistoryAccount(@PathParam("num") Long num);

    /**
     * 同步历史数据
     *
     */
    @GET
    @Path("/sync_old")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    void syncAccount();

    /**
     * 新增登录账号
     *
     * @param account
     * @return
     */
    @POST
    @Path("/save")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation("新增登录账号")
    Account save(Account account);

    /**
     * 更新登录账号
     *
     * @param account
     * @return
     */
    @PUT
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation("更新登录账号")
    Account update (Account account);



    /**
     * 更换登录账号
     *
     * @param mobile
     *
     * @return
     */
    @PUT
    @Path("/update/mobile/{mobile}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation("更新登录账号手机号")
    Account changeMobile(@PathParam("mobile") String mobile);

    /**
     * 更换登录密码
     *
     * @param id 登录账号id
     * @param oldPassword 原密码
     * @param newPassword 新密码
     * @return
     */
    @PUT
    @Path("/update/password/{id}/{oldPassword}/{newPassword}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation("更换登录密码")
    Account changePassword(@PathParam("id") Long id,
                           @PathParam("oldPassword") String oldPassword,
                           @PathParam("newPassword") String newPassword);


    /**
     * 重置用户密码
     * @return
     */
    @PUT
    @Path("/reset/password/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation("重置用户密码")
    boolean resetPassword(@PathParam("id") Long id);

    /**
     * 批量重置员工密码
     * @param ids
     */
    @POST
    @Path("/reset/passwords")
    @Produces({MediaType.APPLICATION_JSON})
    @ApiOperation("批量重置用户密码")
    void resetPasswords(@PathParam("ids") Map<String, Object> ids);


}
