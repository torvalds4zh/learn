package com.weibangong.message.client;

/**
 * Created by jianghailong on 15/12/3.
 */
public final class MessageConstants {

    public static final String USER_ID = "UserId";

    public static final String USER_IDS = "UserIds";

    public static final class Distributor {

        public static final String NOTIFICATION_TYPE = "NotificationType";

    }

    public static final class SMS {

        public static final String TEMPLATE_CODE = "TemplateCode";

        public static final String PHONE = "Phone";

        public static final String PHONES = "Phones";

        public static final String CONTENT = "Content";

        public static final String GATE = "Gate";

        public static final String VOICE_CODE = "voiceCode";
    }

    public static final class APNS {

        public static final String DeviceToken = "DeviceToken";

        public static final String AlertBody = "AlertBody";

        public static final String Sound = "Sound";

        public static final String Badge = "Badge";

        public static final String Properties = "Properties";

    }

    public static final class MQTT {

        public static final String TOPIC = "Topic";
    }
}
