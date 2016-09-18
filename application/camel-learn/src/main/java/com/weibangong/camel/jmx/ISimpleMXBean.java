package com.weibangong.camel.jmx;

import java.io.Serializable;

/**
 * Created by chenbo on 16/8/29.
 */
public interface ISimpleMXBean extends Serializable {
    void tick() throws Exception;
}
