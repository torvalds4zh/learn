package com.weibangong.framework.dispatcher;

import org.apache.camel.PropertyInject;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import com.weibangong.framework.dispatcher.processor.DispatcherProcessor;

/**
 * Created by jianghailong on 15/12/28.
 */
public class DispatcherRouteBuilder extends RouteBuilder {

    @PropertyInject("api")
    private String api;

    @Override
    public void configure() throws Exception {
        from("servlet:///?matchOnUriPrefix=false&httpMethodRestrict=POST")
                .process(new DispatcherProcessor(api)).marshal().json(JsonLibrary.Jackson);
    }
}
