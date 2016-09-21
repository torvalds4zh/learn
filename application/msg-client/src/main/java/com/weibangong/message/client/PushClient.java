package com.weibangong.message.client;

import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class PushClient {

    private Channel channel;
    private Connection connection;

    private String addresses;
    private String username;
    private String password;
    private String virtualHost;

    private static final String MSG = "ha.msg.distributor";
    private static final String SMS = "ha.msg.sms";
    private static final String SECRETARY = "ha.msg.secretary";


    public static PushClient create() {
        return new PushClient();
    }

    public PushClient addresses(String addresses) {
        this.addresses = addresses;
        return this;
    }

    public PushClient username(String username) {
        this.username = username;
        return this;
    }

    public PushClient password(String password) {
        this.password = password;
        return this;
    }

    public PushClient virtualHost(String virtualHost) {
        this.virtualHost = virtualHost;
        return this;
    }

    public PushClient end() {
        log.info("[PushClient]Init...");
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername(username);
        factory.setPassword(password);
        factory.setVirtualHost(virtualHost);
        factory.setAutomaticRecoveryEnabled(true);
        factory.setNetworkRecoveryInterval(5000);

        try {
            connection = factory.newConnection(Address.parseAddresses(addresses));
            channel = connection.createChannel();
            channel.queueDeclare(MSG, true, false, false, null);
            channel.queueDeclare(SMS, true, false, false, null);
        } catch (Exception e) {
            log.error("[PushClient]Can't connect to Rabbitmq server.", e);
        }
        return this;
    }

    public void close() {
        log.info("[PushClient]Close...");
        try {
            connection.close();
            channel = null;
        } catch (Throwable t) {
            log.info("[PushClient]Ignore exception {}", t);
        }
    }

    public void smsPush(PushMessage pushMessage) {
        if (pushMessage.getTarget().isEmpty() || pushMessage.getType().isEmpty()) {
            log.warn("[PushClient/SMS]Params error");
            return;
        }

        try {
            Map<String, Object> headers = new HashMap<>();
            headers.putAll(pushMessage.getHeaders());
            headers.put("TemplateCode", pushMessage.getType());
            headers.put("phones", pushMessage.getTarget());
            AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                    .headers(headers).build();
            channel.basicPublish("", SMS, properties, null);
            log.info("[PushClient/SMS]TemplateCode:{}|Phones:{}", pushMessage.getType(), pushMessage.getTarget());
        } catch (Exception e) {
            log.error("[PushClient]Failed to publish to Rabbitmq.", e);
        }
    }

    public void voicePush(PushMessage pushMessage) {
        if (pushMessage.getTarget().isEmpty() || pushMessage.getType().isEmpty()) {
            log.warn("[PushClient/Voice]Params error");
            return;
        }

        try {
            Map<String, Object> headers = new HashMap<>();
            headers.put("gate", 0);
            headers.put("voiceCode", pushMessage.getType());
            headers.put("phones", pushMessage.getTarget());
            AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                    .headers(headers).build();
            channel.basicPublish("", SMS, properties, null);
            log.info("[PushClient/Voice]Code:{}|Phones:{}", pushMessage.getType(), pushMessage.getTarget());
        } catch (Exception e) {
            log.error("[PushClient]Failed to publish to Rabbitmq.", e);
        }
    }

    public void wbgPush(PushMessage pushMessage) {
        if (pushMessage.getTarget().isEmpty() || pushMessage.getType().isEmpty()) {
            log.warn("[PushClient/WBG]Params error");
            return;
        }

        try {
            Map<String, Object> headers = new HashMap<>();
            headers.putAll(pushMessage.getHeaders());
            headers.put("NotificationType", pushMessage.getType());
            headers.put("userIds", pushMessage.getTarget());
            AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                    .headers(headers).build();
            channel.basicPublish("", MSG, properties, pushMessage.getBody().getBytes());
            log.info("[PushClient/WBG]NotificationType:{}|UserIds:{}", pushMessage.getType(), pushMessage.getTarget());
        } catch (Exception e) {
            log.error("[PushClient]Failed to publish to Rabbitmq.", e);
        }
    }

    public void secretaryPush(PushMessage pushMessage) {
        if (pushMessage.getTarget().isEmpty() || pushMessage.getType().isEmpty()) {
            log.warn("[PushClient/SECRETARY]Params error");
            return;
        }

        try {
            Map<String, Object> headers = new HashMap<>();
            headers.putAll(pushMessage.getHeaders());
            headers.put("userId", pushMessage.getTarget());
            AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                    .headers(headers).build();
            channel.basicPublish("", SECRETARY, properties, pushMessage.getBody().getBytes());
            log.info("[PushClient/WBG]NotificationType:{}|UserIds:{}", pushMessage.getType(), pushMessage.getTarget());
        } catch (Exception e) {
            log.error("[PushClient]Failed to publish to Rabbitmq.", e);
        }
    }
}
