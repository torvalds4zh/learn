package com.weibangong.mybatis.test;

import com.weibangong.mybatis.mapper.UserMapper;
import com.weibangong.mybatis.model.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by haizhi on 15/8/27.
 */
public class MyBatisFirstTest {

    ApplicationContext ctx = null;

    @Before
    public void init() {
        ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
    }

    @Test
    public void testFindUserById() {
        UserMapper userMapper = (UserMapper) ctx.getBean("userMapper");
        User user = userMapper.findById(2);
        System.out.println("+++++++++++++++++++++");
        System.out.println(user);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        System.out.println(dateFormat.format(user.getCreateTime()));
    }

    @Test
    public void testInsert() {
        UserMapper userMapper = (UserMapper) ctx.getBean("userMapper");

        User user = new User();
        user.setName("abcdsada");
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        userMapper.insert(user);

        User user2 = userMapper.findById(4);
        System.out.println("+++++++++++++++++++++");
        System.out.println(user2);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        System.out.println(dateFormat.format(user2.getCreateTime()));
    }

    @Test
    public void testDelete() {

    }

    @Test
    public void testUpdate() {

    }

}
