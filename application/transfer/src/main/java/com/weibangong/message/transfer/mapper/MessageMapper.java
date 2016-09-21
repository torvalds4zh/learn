package com.weibangong.message.transfer.mapper;

import com.weibangong.message.transfer.model.Message;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by jianghailong on 16/1/4.
 */
public interface MessageMapper {

    int insert(Message record);

    @ResultMap("BaseResultMap")
    @Select("SELECT * FROM t_message ORDER BY ID limit #{offset}, #{limit}")
    List<Message> select(@Param("offset") long offset, @Param("limit") int limit);

    @Select("SELECT COUNT(id) FROM t_message")
    long countAll();

    @Update("UPDATE t_message SET content=#{content} WHERE id=#{id}")
    int updateContent(Message message);

    @ResultMap("BaseResultMap")
    @Select("SELECT * FROM t_message WHERE id = #{id}")
    Message selectById(@Param("id") long id);

    @Update("UPDATE t_message SET content_type = 5, content = NULL WHERE id = #{id}")
    int cancel(@Param("id") long id);

}
