package com.weibangong.framework.auth.interceptor;

import lombok.Data;

@Data
public class AuthContext {

    private static ThreadLocal<Client> contextThreadLocal = new ThreadLocal<>();

    public static Client get() {
        return contextThreadLocal.get();
    }

    public static void put(Client client) {
        contextThreadLocal.set(client);
    }

    public static void clear () {
        contextThreadLocal.remove();
    }

}
