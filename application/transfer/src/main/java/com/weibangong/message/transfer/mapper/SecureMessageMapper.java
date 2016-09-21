package com.weibangong.message.transfer.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import com.weibangong.message.transfer.model.SecureMessage;

import java.util.List;

/**
 * Created by jianghailong on 16/1/8.
 */
public interface SecureMessageMapper {

    @ResultMap("BaseResultMap")
    @Select("SELECT * FROM secure_message WHERE id = #{id}")
    SecureMessage selectByPrimaryKey(@Param("id") Long id);

    int insert(SecureMessage message);

    int insertAll(List<SecureMessage> messages);

}
