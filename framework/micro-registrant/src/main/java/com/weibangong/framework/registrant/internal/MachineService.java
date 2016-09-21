package com.weibangong.framework.registrant.internal;

import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by jianghailong on 16/5/9.
 */
@Slf4j
public class MachineService {

    private static final int REMOTE_PORT = 1099;

    public String getHostAddress() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getPort() {
        return REMOTE_PORT;
    }
}
