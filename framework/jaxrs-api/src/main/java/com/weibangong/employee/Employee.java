package com.weibangong.employee;

import com.fasterxml.jackson.annotation.JsonView;
import com.weibangong.security.model.Base;
import lombok.Data;
import com.weibangong.security.annotation.FieldVaildation;

import java.util.Date;
import java.util.List;

/**
 * 员工信息实体
 * <p/>
 * Created by zhangping on 16/2/15.
 */
@Data
public class Employee extends Base {

    private Long ownerId;

    /**
     * 员工所在公司名称
     */
    private String tenantFullname;


    /**
     * 员工所在公司的状态
     */
    private int tenantStatus;

    /**
     * email
     */
    @JsonView({DetailView.class})
    private String email;

    /**
     * 手机号码
     */
    @FieldVaildation(desc = "手机号", empty = false, length = 11)
    private String mobile;

    /**
     * 用户编号
     */
    private String sn;

    /**
     * 用户全名
     */
    @FieldVaildation(desc = "姓名", empty = false, length = 255)
    private String fullname;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 用户隐私设置
     */
    private Integer privacy;

    /**
     * 相关管理权限
     */
    private Long featureAccess;

    /**
     * 用户个人简介
     */
    private String introduction;

    /**
     * 用户职位
     */
    private String jobTitle;

    /**
     * 用户入职时间
     */
    private Date joinedAt;

    /**
     * 拥有的权限值
     */
    private Long access;

    /**
     * 员工所属部门ids
     */
    private List<Long> deptIds;

    private List<String> deptIdPath;

    private List<String> deptNamePath;

    /**
     * 登录账号
     */
    private String loginAccount;

    public interface DetailView {
    }

}
