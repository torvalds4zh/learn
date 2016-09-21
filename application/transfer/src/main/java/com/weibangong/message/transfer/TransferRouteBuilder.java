package com.weibangong.message.transfer;

import lombok.Data;
import org.apache.camel.ExchangePattern;
import org.apache.camel.PropertyInject;
import org.apache.camel.builder.RouteBuilder;

/**
 * Created by chenbo on 16/09/21.
 */
@Data
public class TransferRouteBuilder extends RouteBuilder {

    @PropertyInject(value = "httpOnly", defaultValue = "false")
    private Boolean httpOnly;

    @PropertyInject(value = "mqtt.throttle", defaultValue = "1000")
    private long throttle;

    @PropertyInject(value = "node.id", defaultValue = "-1")
    private String nodeId;

    @Override
    public void configure() throws Exception {
        if (nodeId.equals("-1")) {
            nodeId = System.getProperty("node.id");
            if (nodeId == null) {
                nodeId = System.getenv("NODE_ID");
            }
        }

        if (!httpOnly) {
            from("mqtt:receive?host={{mqtt.host}}&" +
                    "userName={{mqtt.user}}&password={{mqtt.pass}}&" +
                    "subscribeTopicName=messages/add&qualityOfService=AtMostOnce&reconnectDelay=1")
                    .process("receiveProcessor");

            from("mqtt:receive?host={{mqtt.host}}&" +
                    "userName={{mqtt.user}}&password={{mqtt.pass}}&" +
                    "subscribeTopicName=messages/service&qualityOfService=AtMostOnce&reconnectDelay=1")
                    .process("serviceProcessor");
        }

        from("direct:delivery").to("mqtt://delivery?host={{mqtt.host}}&" +
                "userName={{mqtt.user}}&password={{mqtt.pass}}&qualityOfService=AtMostOnce&reconnectDelay=1");

        from("rabbitmq://{{rabbitmq.host}}:{{rabbitmq.port}}/transfer-" + nodeId + "?" +
                "username={{rabbitmq.user}}&password={{rabbitmq.pass}}&vhost={{rabbitmq.vhost}}&" +
                "queue=transfer-" + nodeId + "&durable=true&autoDelete=false&threadPoolSize=2&" +
                "autoAck=false&automaticRecoveryEnabled=true&queueArgsConfigurer=#queueArgs")
                .streamCaching().throttle(throttle).timePeriodMillis(1000).process("msgProcessor");

        from("direct:distributor").setExchangePattern(ExchangePattern.InOnly).to("rabbitmq://{{rabbitmq.host}}:{{rabbitmq.port}}/transfer.distributor?" +
                "username={{rabbitmq.user}}&password={{rabbitmq.pass}}&vhost={{rabbitmq.vhost}}&" +
                "queue=ha.msg.distributor&durable=true&autoDelete=false&" +
                "automaticRecoveryEnabled=true");

        from("direct:msg").setExchangePattern(ExchangePattern.InOnly).to("rabbitmq://{{rabbitmq.host}}:{{rabbitmq.port}}/transfer.msg?" +
                "username={{rabbitmq.user}}&password={{rabbitmq.pass}}&vhost={{rabbitmq.vhost}}&" +
                "queue=ha.mqtt.msg&durable=true&autoDelete=false&" +
                "automaticRecoveryEnabled=true");

//        from("direct:solrSync").setExchangePattern(ExchangePattern.InOnly).to("rabbitmq://{{rabbitmq.host}}:{{rabbitmq.port}}/search.ha.message.index.bucket?" +
//                "username={{rabbitmq.user}}&password={{rabbitmq.pass}}&vhost={{rabbitmq.vhost}}&" +
//                "queue=ha.message.index.bucket&durable=true&autoDelete=false&" +
//                "automaticRecoveryEnabled=true");

        from("direct:httpMessageAdd").process("receiveProcessor");

        from("direct:httpMessageService").process("serviceProcessor");

//        from("direct:robot").setExchangePattern(ExchangePattern.InOnly)
//                .to("rabbitmq://{{rabbitmq.host}}:{{rabbitmq.port}}/transfer.ha.message.robot?" +
//                        "username={{rabbitmq.user}}&password={{rabbitmq.pass}}&vhost={{rabbitmq.vhost}}&" +
//                        "queue=ha.message.robot&durable=true&autoDelete=false&" +
//                        "automaticRecoveryEnabled=true");
    }

}
