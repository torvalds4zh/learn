package com.weibangong.mybatis.mapper;

import com.weibangong.mybatis.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

/**
 * Created by chenbo on 15/8/28.
 */
public interface UserMapper {
    String tbName = " t_user ";
    String allFields = " `id`, `name`, `created_at`, `updated_at` ";
    String noIdFields = " `name`, `created_at`, `updated_at` ";

    @ResultMap("BaseResultMap")
    @Select("select " + allFields
            + " from " + tbName
            + " where id=#{id}")
    User findById(@Param("id") Integer id);

    @Insert("insert into " + tbName + "(" + noIdFields + ")"
            + " values " +
            "   (#{user.name}, #{user.createTime,jdbcType=TIMESTAMP}, #{user.updateTime,jdbcType=TIMESTAMP})")
    void add(@Param("user") User user);

    void insert(User user);
}
