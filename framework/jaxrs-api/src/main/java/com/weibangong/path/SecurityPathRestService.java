package com.weibangong.path;

import com.weibangong.company.Company;
import com.weibangong.dept.Dept;
import com.weibangong.employee.Employee;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by zhangping on 16/3/14.
 */
@Path("/")
public interface SecurityPathRestService {

    /**
     * 新增path
     *
     * @param securityPath
     * @return
     */
    @POST
    @Path("/path/save")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    SecurityPath save(SecurityPath securityPath);

    /**
     * 批量新增path
     *
     * @param securityPaths
     * @return
     */
    @POST
    @Path("/paths/save")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    void batchSave(List<SecurityPath> securityPaths);


    /**
     * 新增company时 存储path
     * 1 :
     */
    @Path("/company")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    void save(Company company);

    /**
     * 新增company时 存储path
     * 1 :
     */
    @Path("/company/batch")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    void saveBatch(List<Company> companys);


    /**
     * 新增employee时 存储path
     * 1 :
     */
    @Path("/employee")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    void save(Employee employee);

    /**
     * 新增dept时 存储path
     *
     * @param dept
     */
    @Path("/dept")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    void save(Dept dept);

    void update(SecurityPath securityPath);

    @Path("/employee")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    void update(Employee employee);

    @Path("/dept")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    void update(Dept dept);

    /**
     * 获取部门下的子部门ID
     */
    @Path("/dept/child/{tenantId}/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    List<Long> getChildIds(@PathParam("tenantId") Long tenantId, @PathParam("id") Long id);

    /**
     * 获取部门下所有人员 包括子部门下人员
     *
     * @param tenantId
     * @param id
     * @return
     */
    @Path("/dept/employees/{tenantId}/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    List<SecurityPath> getEmployeeByDeptId(@PathParam("tenantId") Long tenantId, @PathParam("id") Long id);

    /**
     * 获取公司下所有的员工 path
     *
     * @param tenantId
     * @return
     */
    @Path("/employees/{tenantId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    List<SecurityPath> getEmployeeByTenantId(@PathParam("tenantId") Long tenantId);

    /**
     * 部门成员变更
     *
     * @param deptMemberVo tenantId            公司id
     * @param deptMemberVo id                  部门id
     * @param deptMemberVo addMemberIds        待新增成员id集合
     * @param deptMemberVo removeMemberPathIds 待删除部门成员path id集合
     * @return
     */
    @Path("/dept/member")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    void deptMemberChange(DeptMemberVo deptMemberVo);


    List<Long> getChildIds(Dept dept);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    SecurityPath get();

    @GET
    @Path("/{tenantId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    List<SecurityPath> get(@PathParam("tenantId") Long tenantId);

    @PUT
    @Path("/del/dept")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    void delete(Dept dept);

    @PUT
    @Path("/del/employee")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    void delete(Employee employee);


    /**
     * 群组删除时 同步删除group path 及 群组下成员path
     *
     * @param id
     */
    @DELETE
    @Path("/group/{tenantId}/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    void deleteGroupPath(@PathParam("tenantId") Long tenantId, @PathParam("id") Long id);

    /**
     * 群组下踢人
     *
     * @param tenantId
     * @param id
     * @param removeUserIds
     */
    @DELETE
    @Path("/group/{tenantId}/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    void deleteGroupUserPath(@PathParam("tenantId") Long tenantId, @PathParam("id") Long id, List<Long> removeUserIds);


    /**
     * 获取某个(多个)部门下有哪些员工
     */

    /**
     * 获取某个(多个)群组下有哪些员工
     */

    /**
     * 获取某个(多个)人所属哪些部门
     */

    /**
     * 获取某个(多个)人所属哪些群组
     */

    /**
     * 获取员工的部门path
     *
     * @param tenantId 公司ID
     * @param id       员工ID
     * @return
     */
    @GET
    @Path("/employee/{tenantId}/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    List<SecurityPath> getDeptPathById(@PathParam("tenantId") Long tenantId, @PathParam("id") Long id);

    /**
     * 获取群组有哪些人
     *
     * @param tenantId 公司ID
     * @param id       员工ID
     * @return
     */
    @GET
    @Path("/group/{tenantId}/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    List<SecurityPath> getEmployeeByGroupId(@PathParam("tenantId") Long tenantId, @PathParam("id") Long id);


}
