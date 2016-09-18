package com.weibangong.osgi.felix;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Created by chenbo on 16/8/25.
 */
public class TrackerActivator implements BundleActivator {
    private HelloTracker helloTracker;
    ServiceRegistration serviceRegistration;

    public void start(BundleContext context) throws Exception {
        System.out.println("service registered");
        //开启服务跟踪器
        helloTracker = new HelloTracker(context);
        helloTracker.open();

        //注册服务
        serviceRegistration = context.registerService(HelloService.class, new HelloServiceImpl(), null);

        HelloService helloService = (HelloService) helloTracker.getService();
        if(helloService != null){
            helloService.sayHello("world");
        }
        ServiceReference serviceReference = context.getServiceReference(HelloService.class);
        HelloService helloService1 = (HelloService) helloTracker.getService(serviceReference);
        if(helloService1 != null){
            helloService1.sayHello("wooooorld!");
        }
    }

    public void stop(BundleContext context) throws Exception {
        System.out.println("Stopping service...");
        //关闭服务跟踪器
        if(helloTracker != null){
            helloTracker.close();
        }
        //注销服务
        serviceRegistration.unregister();
    }

    private class HelloTracker extends ServiceTracker{
        private BundleContext bundleContext;

        public HelloTracker(BundleContext context) {
            super(context, HelloService.class.getName(), null);
            this.bundleContext = context;
        }

        @Override
        public Object addingService(ServiceReference reference) {
            System.out.println("adding service: " + reference.getBundle().getSymbolicName());
            return super.addingService(reference);
        }

        @Override
        public void removedService(ServiceReference reference, Object service) {
            System.out.println("removing service: " + reference.getBundle().getSymbolicName());
            super.removedService(reference, service);
        }
    }
}
