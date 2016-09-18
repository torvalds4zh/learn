package com.weibangong.osgi.cxf;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by chenbo on 16/8/30.
 */
@Path("/")
public interface HelloService {

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    String hello();

    @Path("/hello/{name}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    String hi(@PathParam("name") String name);
}
