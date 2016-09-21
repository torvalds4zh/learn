package com.weibangong.message.client;

import java.util.HashSet;
import java.util.Set;

public class PushExample {

    public static void main(String[] args) {

        PushClient pushClient = PushClient.create()
                .addresses(Constant.TEST_MQ_SERVER).username("wbgrmq").password("wbgrmq").virtualHost("/").end();

        Set<Long> ids = new HashSet<>();
        ids.add(30196L);

        /* 发短信,所示为必填 */
        PushMessage.create()
                .type("VERIFY_CODE").target("15698895495").header("code", "123456").smsPush(pushClient);

        /* 语音验证码,所示为必填 */
        PushMessage.create()
                .type("123456").target("15698895495").voicePush(pushClient);

        /* 微秘推送,所示为必填 */
        PushMessage.create()
                .type(PushType.WeimiMsgPush.name()).target(ids).header("tenantId", 30195).body("微秘测试").wbgPush(pushClient);

        /* 动作推送,所示为必填 */
        PushMessage.create()
                .type(PushType.ActionPush.name()).target(ids).body("").wbgPush(pushClient);

        /* 提醒推送,所示为必填 */
        PushMessage.create()
                .type(PushType.NotificationCenter.name()).target(ids).body("提醒").wbgPush(pushClient);


        // 微密卡片推送 --------------begin----------------------
//                PushMessage.create().header("sourceId", "538334")
//                        .header("targetId", userAndTenant[0])
//                        .header("sourceType", "7")
//                        .header("targetType", "2")
//                        .header("tenantId",userAndTenant[1])
//                        .header("contentType", 7)
//                        .body(content).slavePush(pushClient);
        // 微密卡片推送 --------------end----------------------


    }
}
