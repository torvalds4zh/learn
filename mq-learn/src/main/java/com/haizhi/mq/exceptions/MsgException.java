package com.haizhi.mq.exceptions;

import java.io.Serializable;

/**
 * wbg运行期异常
 *
 * Created by xiaolezheng on 15/8/27.
 */
public class MsgException extends RuntimeException implements Serializable{

    private static final long serialVersionUID = -7080413732151560079L;

    public MsgException() {
    }

    public MsgException(String message) {
        super(message);
    }

    public MsgException(String msgTemplate, Object... args){
        super(String.format(msgTemplate,args));
    }

    public MsgException(String message, Throwable cause) {
        super(message, cause);
    }

    public MsgException(Throwable cause, String msgTemplate, Object... args){
        super(String.format(msgTemplate,args), cause);
    }

    public MsgException(Throwable cause) {
        super(cause);
    }
}
