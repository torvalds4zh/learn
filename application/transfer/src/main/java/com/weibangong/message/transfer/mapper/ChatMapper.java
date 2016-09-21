package com.weibangong.message.transfer.mapper;

import com.weibangong.message.transfer.model.Chat;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by jianghailong on 16/1/4.
 */
public interface ChatMapper {

    @ResultMap("BaseResultMap")
    @Select("select * " +
            "from t_chat " +
            "where source_id = #{sourceId} " +
            "and target_id = #{targetId} " +
            "and status != 3 ")
    Chat selectByPrimaryKey(@Param("sourceId") Long sourceId, @Param("targetId") Long targetId);

    @ResultMap("BaseResultMap")
    @Select("select * " +
            "from t_chat " +
            "where target_id = #{targetId} " +
            "and status != 3 ")
    List<Chat> selectByTargetId(@Param("targetId") Long targetId);

    int insert(Chat record);

    int updateByPrimaryKeySelective(Chat record);

    int updateByPrimaryKeySelectiveAndIncUnread(Chat record);

    int updateByTargetIdSelectiveAndIncUnread(Chat record);
}
