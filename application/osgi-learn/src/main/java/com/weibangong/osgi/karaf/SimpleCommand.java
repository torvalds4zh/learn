package com.weibangong.osgi.karaf;

/**
 * Created by chenbo on 16/8/25.
 */

/**
 * 普通类在blueprint.xml中经过配置也可以做自定义命令
 */
public class SimpleCommand {
    public void first(){
        System.out.println("first");
    }

    public void second(){
        System.out.println("second");
    }

    public void hello(String name){
        System.out.println("Hello " + name);
    }
}
