package com.weibangong.mybatis.blueprint;

import org.apache.ibatis.session.SqlSessionFactory;

/**
 * Created by jianghailong on 16/1/4.
 */
public class MapperFactory {

    public static <T> T get(SqlSessionFactory sessionFactory, Class<T> clazz) {
        return new SqlSessionTemplate(sessionFactory).getMapper(clazz);
    }
}
