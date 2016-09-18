package com.haizhi.mq.test.custom;

import java.util.Date;

/**
 * Created by chenbo on 15/11/12.
 */
public class User {
    private long id;
    private String name;
    private Date birth;

    public User(){}

    public User(long id, String name, Date birth) {
        this.id = id;
        this.name = name;
        this.birth = birth;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birth=" + birth +
                '}';
    }
}
