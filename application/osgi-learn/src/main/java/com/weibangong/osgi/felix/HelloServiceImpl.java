package com.weibangong.osgi.felix;

/**
 * Created by chenbo on 16/8/22.
 */
public class HelloServiceImpl implements HelloService {

    public void sayHello(String words) {
        System.out.println("Hello! " + words);
    }
}
