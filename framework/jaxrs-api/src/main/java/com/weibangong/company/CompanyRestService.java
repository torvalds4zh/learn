package com.weibangong.company;

import io.swagger.annotations.ApiOperation;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

/**
 * Created by jianghailong on 16/2/25.
 */
@Path("/")
public interface CompanyRestService {



    /**
     * 同步插入历史数据
     * @param paras
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    void batchInsertHistoryCompanys (Map<String, Object> paras);

    /**
     * 获取历史数据
     * @param num
     * @return
     */
    @GET
    @Path("/history/{num}")
    @Produces(MediaType.APPLICATION_JSON)
    List<Company> getHistoryCompany(@PathParam("num")Long num);

    /**
     * 获取所有公司数据
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation("获取所有公司信息")
    List<Company> getAllCompany();



    /**
     * 同步历史数据
     *
     */
    @GET
    @Path("/sync_old")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    void syncCompany();

    /**
     * TODO 创建企业账号
     */

    @POST
    @Path("/save")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation("新增企业信息")
    Company save (Company company);

    /**
     * TODO 公司修改名称 简称 修改版本 停用 启用
     */

    @PUT
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation("更新企业信息")
    Company update (Company company);

    @DELETE
    @Path("/delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation("删除企业信息")
    Company delete (@PathParam("id") Long id);

    /**
     * 变更企业简称
     *
     * @param company
     * @return
     */
    @PUT
    @Path("/change/name")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation("更新企业简称")
    Company changeCompanyName (Company company);

    /**
     * 停用企业
     *
     * @return
     */
    @PUT
    @Path("/disable/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation("停用企业信息")
    Company disable (@PathParam("id")Long id);


    /**
     * 恢复企业
     *
     * @return
     */
    @PUT
    @Path("/enable/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation("启用企业信息")
    Company enable (@PathParam("id")Long id);

    /**
     * 查询企业
     *
     * @return
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation("获取企业信息")
    Company get (@PathParam("id")Long id);






}
