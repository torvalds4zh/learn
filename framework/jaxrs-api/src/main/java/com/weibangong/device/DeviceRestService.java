package com.weibangong.device;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by jianghailong on 15/12/31.
 */
@Path("/")
public interface DeviceRestService {

    /**
     * 注册设备
     *
     * @param device 设备信息
     * @return
     * @see Device
     */
    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response register(Device device);

    /**
     * 注销设备
     *
     * @param id 设备号
     * @return 成功返回1，失败返回0
     */
    @DELETE
    @Path("/unregister/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response unregister(@PathParam("id") String id);

    /**
     * 删除设备:根据令牌
     *
     * @param token
     * @return
     */
    @DELETE
    @Path("/token/{token}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response deleteByToken(@PathParam("token") String token);

    /**
     * 删除设备:根据设备ID
     *
     * @param id
     * @return
     */
    @DELETE
    @Path("/device_id/{deviceId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response deleteById(@PathParam("deviceId") String id);

    /**
     * 删除设备:根据imei
     *
     * @param imei
     * @return
     */
    @DELETE
    @Path("/imei/{imei}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response deleteByImei(@PathParam("imei") String imei);


    @GET
    @Path("/by_owner_id/{ownerId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Response getDevicesByOwnerId(@PathParam("ownerId") String ownerId);

    @POST
    @Path("/by_owner_ids")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Response getDevicesByOwnerIds(List<String> ownerIds);

    @GET
    @Path("/token/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Response getDevicesByToken(@PathParam("token") String token);

    @GET
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response getDeviceById(@PathParam("id") String id);

    /**
     * 分配mqttserver
     *
     * @param userId
     * @return
     */
    @GET
    @Path("/allocate_mqtt/{user_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Response allocateMqttServer(@PathParam("user_id") String userId);

    /**
     * 失效设备, 优先根据id失效设备,如果device.id为空,则根据device.ownerId失效设备
     *
     * @param device
     * @return
     * @see Device
     */
    @DELETE
    @Path("/expired")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Response expiredDevice(Device device);

    /**
     * 更新状态(device.status),优先根据id失效设备,如果device.id为空,则根据device.ownerId失效设备
     *
     * @param device
     * @return
     * @see DeviceStatus
     * @see Device
     */
    @POST
    @Path("/status")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Response updateStatus(Device device);


}
