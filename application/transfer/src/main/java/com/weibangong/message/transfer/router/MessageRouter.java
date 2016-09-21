package com.weibangong.message.transfer.router;


import com.weibangong.message.transfer.model.Message;

/**
 * Created by jianghailong on 16/1/4.
 */
public interface MessageRouter {

    void process(Message message);
}
