package com.weibangong.employee;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

/**
 * Created by jianghailong on 16/2/25.
 */
@Path("/")
public interface EmployeeRestService {

    /**
     * 同步历史数据
     */
    @GET
    @Path("/sync_old")
    @Produces(MediaType.APPLICATION_JSON)
    void syncEmployees();

    /**
     * 按登录账号id 获取对应的所有员工
     * <pre>
     *   内部调用 不需要认证
     * </pre>
     * @param ownerId
     * @return
     */
    @GET
    @Path("/owner/{ownerId}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation("按登录账号ownerId查询员工信息")
    List<Employee> getEmployeesByOwnerId(@PathParam("ownerId")Long ownerId);


    /**
     * 按邮箱 获取员工信息
     * <pre>
     *   内部调用 不需要认证
     * </pre>
     * @param email
     * @return
     */
    @GET
    @Path("/email/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation("按邮箱查询员工信息")
    List<Employee> getEmployeeByEmail(@PathParam("email")String email);

    /**
     * 按员工id 获取员工信息
     * @param id
     * @return
     */
    @GET
    @Path("/id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation("按员工id查询员工信息")
    Employee getEmployeesById(@PathParam("id")Long id);


    /**
     * 同步插入历史数据
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    void batchInsertHistoryEmployees(Map<String, Object> params);

    /**
     * 获取历史数据
     * @param num
     * @return
     */
    @GET
    @Path("/history/{num}")
    @Produces(MediaType.APPLICATION_JSON)
    List<Employee> getHistoryEmployees (@PathParam("num")Long num);


    /**
     * 员工信息保存
     *
     * @param employee
     * @return
     */
    @POST
    @Path("/save")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation("新增员工信息")
    Employee save (@ApiParam Employee employee);

    /**
     *  员工基本信息修改
     *
     * @param employee
     * @return
     */
    @PUT
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation("更新员工信息")
    Employee update (@ApiParam Employee employee);

    /**
     *  员工修改头像
     *
     * @param avator
     * @return
     */
    @PUT
    @Path("/update/avator/{avator}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation("更新员工头像")
    void updateAvator (@PathParam("avator")String avator);

    /**
     * 更新员工的隐私设置 支持批量
     * @param privacySetting
     */
    @PUT
    @Path("/update/privacy")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation("批量隐私设置")
    void updateEmployeesPrivacy (@ApiParam PrivacySetting privacySetting);

    /**
     * TODO 更新员工得所属登录账号id
     *
     * @desc 用于修改登录账号
     */
    @PUT
    @Path("/update/{oldOwner}/{newOwner}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation("更换员工的所关联的登录账号")
    void changeEmployeeOwnerId (@PathParam("oldOwner") Long oldOwner, @PathParam("newOwner") Long newOwner);

    /**
     * 停用
     *
     */
    @PUT
    @Path("/disabled/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation("停用员工")
    void disabled (@PathParam("id") Long id);

    /**
     * 启用
     *
     */
    @PUT
    @Path("/enabled/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation("启用员工")
    void enabled (@PathParam("id") Long id);


    /**
     * 员工导入
     */
    @POST
    @Path("/import")
    @Produces(MediaType.APPLICATION_JSON)
    void importEmployees ();

    /**
     * 员工导出
     */
    @POST
    @Path("/export")
    @Produces(MediaType.APPLICATION_JSON)
    void exportEmployees ();


    /**
     * 员工删除
     *
     * @param id
     */
    @DELETE
    @Path("/delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation("员工删除")
    void delete (@PathParam("id") Long id);


    /**
     * 获取所有员工
     *
     * @return
     */
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation("获取所有的员工信息")
    List<Employee> getAllEmployee ();

    /**
     * 按指定的公司id 获取公司所有的员工
     *
     * @param companyId
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    List<Employee> getAllEmployee (Long companyId);

    /**
     * 批量删除员工,或者停用员工
     * @return
     */
    @POST
    @Path("/update/employees")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation("批量停用 启用 删除员工信息")
    void batchUpdateEmployeesStatus(@ApiParam BatchUpdateStatusVo batchUpdateStatusVo);

    /**
     * 修改员工权限
     * @return
     */
    @POST
    @Path("/update/role")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation("修改员工权限")
    void updateEmployeeRole (@ApiParam List<RoleUpdateItem> roleUpdateItems);

    /**
     * 获取抄送黑名单
     */
    @GET
    @Path("/black/list")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation("获取黑名单")
    List<BlackList> getBlackList(@QueryParam("tenantId") Long tenantId);

    /**
     * 增加黑名单
     * @return
     */
    @POST
    @Path("/black/save")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation("添加黑名单")
    BlackList addBlackList(@ApiParam BlackList blackList);

    /**
     * 移除黑名单
     * @param id
     */
    @POST
    @Path("/black/remove/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation("移除黑名单")
    void removeBlackList(@PathParam("id") Long id);
}
