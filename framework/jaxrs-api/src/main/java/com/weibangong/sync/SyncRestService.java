package com.weibangong.sync;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/")
public interface SyncRestService {

    @GET
    @Path("/{version}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    int sync(@PathParam("version") Long version);
    //todo 这需要补完

}
