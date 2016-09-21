package com.weibangong.mybatis.blueprint.mappers;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import com.weibangong.mybatis.blueprint.model.SecureKey;

/**
 * Created by jianghailong on 16/1/9.
 */
public interface SecureKeyMapper {

    int insert(SecureKey secureKey);

    @ResultMap("BaseResultMap")
    @Select("SELECT * FROM secure_key WHERE id = #{id}")
    SecureKey selectByPrimaryKey(@Param("id") Long id);

    @ResultMap("BaseResultMap")
    @Select("SELECT * FROM secure_key WHERE key_index = #{keyIndex} AND action=#{action}")
    SecureKey selectByKeyIndexAndAction(@Param("keyIndex") Long keyIndex,
                                     @Param("action") String scope);

    @ResultMap("BaseResultMap")
    @Select("SELECT * FROM secure_key WHERE key_index = #{keyIndex} ")
    SecureKey selectByKeyIndex(@Param("keyIndex") Long keyIndex);
}
