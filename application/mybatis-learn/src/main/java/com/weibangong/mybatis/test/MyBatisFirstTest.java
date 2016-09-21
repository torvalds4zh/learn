package com.weibangong.mybatis.test;

import com.mysql.jdbc.TimeUtil;
import com.weibangong.mybatis.mapper.UserMapper;
import com.weibangong.mybatis.model.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        User user = userMapper.findById(3);
        System.out.println("+++++++++++++++++++++");
        System.out.println(user);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        System.out.println(dateFormat.format(user.getCreateTime()));
    }

    @Test
    public void testInsert() {
        UserMapper userMapper = (UserMapper) ctx.getBean("userMapper");

        User user = new User();
        user.setName("chenbo");
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        userMapper.insert(user);

        User user2 = userMapper.findById(2);
        System.out.println("+++++++++++++++++++++");
        System.out.println(user2);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        System.out.println(dateFormat.format(user2.getCreateTime()));
    }

    @Test
    public void testDelete() {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        StringBuffer buf = new StringBuffer();
        buf.append(dateFormat.format(now));
        int nanos = (int) ((now.getTime() % 1000) * 1000000);
        if (nanos != 0) {
            buf.append('.');
            //官方驱动这里是通过版本去format的,mysql版本大于5.6.4才取毫秒
            buf.append(TimeUtil.formatNanos(nanos, true, true));
        }
        buf.append('\'');
        System.out.println(buf.toString());
    }

    @Test
    public void testUpdate() {
        User user = null;
        List<User> users = new ArrayList<User>();
        users.add(user);
        users.add(null);
        if(users.size() > 0){
            for(User user1 : users){
                System.out.println(user1);
            }
        }

    }

}
