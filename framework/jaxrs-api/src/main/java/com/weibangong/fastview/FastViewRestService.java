package com.weibangong.fastview;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by jianghailong on 16/1/14.
 */
@Path("/")
public interface FastViewRestService {

    @GET
    @Path("list/{viewName}/{viewOwner}")
    @Produces(MediaType.APPLICATION_JSON)
    FastViewPage findByViewName(
            @PathParam("viewName") String viewName,
            @PathParam("viewOwner") String viewOwner,
            @QueryParam("asc") String ascOrderField,
            @QueryParam("desc") String descOrderField,
            @QueryParam("offset") @DefaultValue("0")  Integer offset,
            @QueryParam("limit") @DefaultValue("10") Integer limit);

    @GET
    @Path("listByPeriod/{viewName}/{viewOwner}")
    @Produces(MediaType.APPLICATION_JSON)
    FastViewPage findByViewNameAndPeriod(
            @PathParam("viewName") String viewName,
            @PathParam("viewOwner") String viewOwner,
            @QueryParam("startAt") Long startTime,
            @QueryParam("endAt") Long endTime,
            @QueryParam("updateAt") Long updateAt,
            @QueryParam("asc") String ascOrderField,
            @QueryParam("desc") String descOrderField);

    @GET
    @Path("detail/{viewName}/{viewOwner}")
    @Produces(MediaType.APPLICATION_JSON)
    Object findByPrimaryKey(
            @PathParam("viewName") String viewName,
            @PathParam("viewOwner") String viewOwner,
            @QueryParam("primaryKey") String primaryKey);

    @GET
    @Path("listByKey/{viewName}/{viewOwner}")
    @Produces({MediaType.APPLICATION_JSON})
    FastViewPage findByViewNameAndPrimaryKey(
            @PathParam("viewName") String viewName,
            @PathParam("viewOwner") String viewOwner,
            @QueryParam("primaryKey") String primaryKey,
            @QueryParam("option") @DefaultValue("0") Integer option,
            @QueryParam("asc") String ascOrderField,
            @QueryParam("desc") String descOrderField,
            @QueryParam("offset") @DefaultValue("0")  Integer offset,
            @QueryParam("limit") @DefaultValue("10") Integer limit
    );

    @GET
    @Path("ensureIndexes/{viewName}")
    @Produces({MediaType.APPLICATION_JSON})
    String ensureIndexes(@PathParam("viewName") String viewName);
}
