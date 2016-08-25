//package com.weibangong.osgi.felix;
//
//import org.osgi.service.component.ComponentContext;
//import org.osgi.service.log.LogService;
//
///**
// * Created by chenbo on 16/8/25.
// */
//public class HelloComponent implements HelloService {
//    public void sayHello(String words) {
//        System.out.println("Hello! " + words);
//    }
//
//    public void activate(ComponentContext context) {
//        System.out.println("activate (" + context + ")");
//    }
//
//    public void deactivate(ComponentContext context) {
//        System.out.println("deactivate (" + context + ")");
//    }
//
//    public void modified(ComponentContext context){
//        System.out.println("modified ("+context+")");
//    }
//
//    public void bind(LogService service){
//        service.log(LogService.LOG_DEBUG, "bind");
//    }
//
//    public void unbind(LogService service){
//        service.log(LogService.LOG_DEBUG, "unbind");
//    }
//}
