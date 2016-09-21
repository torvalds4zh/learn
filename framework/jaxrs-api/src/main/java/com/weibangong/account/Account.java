package com.weibangong.account;

import com.weibangong.security.model.Base;
import lombok.Data;

/**
 * 登录账号实体
 *
 * Created by zhangping on 16/2/15.
 */
@Data
public class Account extends Base {

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 密码
     */
    private String password;

    /**
     * 盐值
     */
    private String passwordSalt;

    /**
     * 登录次数
     */
    private int signInCount;

}
