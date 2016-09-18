package com.weibangong.camel.jmx;

import javax.management.AttributeChangeNotification;
import javax.management.NotificationBroadcasterSupport;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by chenbo on 16/8/29.
 */
public class SimpleBean extends NotificationBroadcasterSupport implements ISimpleMXBean{
    public static final long serialVersionUID = 1L;
    private int sequence;
    private int tick;

    public void tick() throws Exception {
        tick ++;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss");
        Date date = sdf.parse("2016-08-29 18:56:51");
        long timeStamp = date.getTime();

        AttributeChangeNotification acn = new AttributeChangeNotification(
                this, sequence++, timeStamp, "attribute change", "stringValue", "string", tick - 1, tick
        );
        sendNotification(acn);
    }
}
