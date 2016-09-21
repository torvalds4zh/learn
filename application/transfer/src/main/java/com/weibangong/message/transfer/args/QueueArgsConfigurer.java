package com.weibangong.message.transfer.args;

import org.apache.camel.component.rabbitmq.ArgsConfigurer;

import java.util.Map;

public class QueueArgsConfigurer implements ArgsConfigurer {
    @Override
    public void configurArgs(Map<String, Object> map) {
        map.put("x-max-priority", 10);
    }
}
