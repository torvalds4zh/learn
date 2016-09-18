//package com.weibangong.osgi.felix;
//
///**
// * Created by chenbo on 16/8/25.
// */
//
//import org.osgi.framework.BundleActivator;
//import org.osgi.framework.BundleContext;
//import org.osgi.framework.ServiceReference;
//import org.osgi.framework.ServiceRegistration;
//import org.osgi.util.tracker.ServiceTracker;
//
///**
// * 声明式服务
// */
//public class DSActivator implements BundleActivator {
//    HelloServiceTracker serviceTracker;
//    ServiceRegistration serviceRegistration;
//
//    public void start(BundleContext context) throws Exception {
//        System.out.println("service registered...");
//        //开启服务跟踪器
//        serviceTracker = new HelloServiceTracker(context);
//        serviceTracker.open();
////        //注册服务
////        serviceRegistration = bundleContext.registerService(HelloService.class, new HelloServiceImpl(), null);
////        //获取b被跟踪的服务
////        HelloService helloService = (HelloService) serviceTracker.getService();
////        if (helloService != null) {
////            System.out.println(helloService.hello("xiaxuan"));
////        }
//        ServiceReference serviceReference = context.getServiceReference(HelloService.class);
//        HelloService testComponent = (HelloService) context.getService(serviceReference);
//        testComponent.sayHello("bingwen");
//    }
//
//    public void stop(BundleContext context) throws Exception {
//
//    }
//
//    private class HelloServiceTracker extends ServiceTracker {
//        private BundleContext bundleContext;
//
//        public HelloServiceTracker(BundleContext context) {
//            super(context, HelloService.class.getName(), null);
//            this.bundleContext = context;
//        }
//
//        @Override
//        public Object addingService(ServiceReference reference) {
//            System.out.println("adding service: " + reference.getBundle().getSymbolicName());
//            return super.addingService(reference);
//        }
//
//        @Override
//        public void removedService(ServiceReference reference, Object service) {
//            System.out.println("removing service: " + reference.getBundle().getSymbolicName());
//            super.removedService(reference, service);
//        }
//    }
//}
