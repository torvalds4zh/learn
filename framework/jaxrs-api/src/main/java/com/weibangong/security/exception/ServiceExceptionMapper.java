package com.weibangong.security.exception;

import com.weibangong.security.http.Result;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * Created by zhangping on 16/2/17.
 */
public class ServiceExceptionMapper implements ExceptionMapper<ServiceException> {

    public ServiceExceptionMapper() {
    }
    @Override
    public Response toResponse(ServiceException exception) {
        Result result = new Result();
        result.setStatus(exception.getCode());
        result.setMessage(exception.getMessage());
        return Response.status(Response.Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON).entity(result).build();
    }
}
