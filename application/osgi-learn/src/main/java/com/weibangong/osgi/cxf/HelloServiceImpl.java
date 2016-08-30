package com.weibangong.osgi.cxf;

/**
 * Created by chenbo on 16/8/30.
 */
public class HelloServiceImpl implements HelloService {
    public String hello() {
        return "hello world!";
    }

    public String hi(String name) {
        return "hello " + name;
    }
}
