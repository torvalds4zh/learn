package com.weibangong.framework.auth.interceptor.exception;

import java.text.MessageFormat;

public class ServiceException extends RuntimeException {

    private String code = "000";

    private String pattern;

    private Object[] arguments;

    public ServiceException(SecurityErrorMsg securityErrorMsg, Object... arguments) {
        this(securityErrorMsg.getCode(), securityErrorMsg.getMessage(), arguments);
    }

    public ServiceException(String code, String pattern, Object... arguments) {
        this.code = code;
        this.pattern = pattern;
        this.arguments = arguments;
    }

    public String getCode() {
        return code;
    }

    public String getPattern() {
        return pattern;
    }

    public Object[] getArguments() {
        return arguments;
    }

    public String getMessage() {
        if (arguments == null || arguments.length == 0 || pattern == null) {
            return pattern;
        }
        return MessageFormat.format(pattern, arguments);
    }
}
