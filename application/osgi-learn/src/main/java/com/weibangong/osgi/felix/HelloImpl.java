package com.weibangong.osgi.felix;

/**
 * Created by chenbo on 16/8/22.
 */
public class HelloImpl implements Hello {
    private String words;

    public HelloImpl(String words) {
        this.words = words;
    }

    @Override
    public void sayHello() {
        System.out.println("Hello! " + this.words);
    }
}
