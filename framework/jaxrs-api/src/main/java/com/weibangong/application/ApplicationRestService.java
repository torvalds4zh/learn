package com.weibangong.application;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by jianghailong on 15/10/28.
 */
@Path("/")
public interface ApplicationRestService {

    /**
     * 获取所有应用
     *
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<Application> getAllApplications();

    /**
     * 创建应用
     *
     * @param application
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Application createApplication(Application application);

    /**
     * 获取应用详情
     *
     * @param id
     * @return
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Application getApplication(@PathParam("id") Long id);

    /**
     * 修改应用
     *
     * @param id
     * @param application
     * @return
     */
    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Application updateApplication(@PathParam("id") Long id, Application application);

    /**
     * 删除应用
     *
     * @param id
     * @return
     */
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Application deleteApplication(@PathParam("id") Long id);

    /**
     * 启用应用
     *
     * @param id
     * @return
     */
    @PUT
    @Path("/{id}/enable")
    @Produces(MediaType.APPLICATION_JSON)
    Application enableApplication(@PathParam("id") Long id);

    /**
     * 禁用应用
     *
     * @param id
     * @return
     */
    @PUT
    @Path("/{id}/disable")
    @Produces(MediaType.APPLICATION_JSON)
    Application disableApplication(@PathParam("id") Long id);

    /**
     * 获取应用的所有key
     *
     * @return
     */
    @GET
    @Path("/{id}/keys")
    @Produces(MediaType.APPLICATION_JSON)
    List<KeySecret> getAllKeySecret(@PathParam("id") Long id);

    /**
     * 申请key
     *
     * @param id
     * @return
     */
    @POST
    @Path("/{id}/keys")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    KeySecret applyKeySecret(@PathParam("id") Long id);

    /**
     * 启用key
     *
     * @param id
     * @return
     */
    @POST
    @Path("/keys/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    KeySecret enableKeySecret(@PathParam("id") Long id);

    /**
     * 禁用key
     *
     * @param id
     * @return
     */
    @POST
    @Path("/keys/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    KeySecret disableKeySecret(@PathParam("id") Long id);
}
