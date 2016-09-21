package com.weibangong.message.transfer.mapper;

import com.weibangong.message.transfer.model.Principal;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

/**
 * Created by jianghailong on 16/1/4.
 */
public interface PrincipalMapper {

    @ResultMap("BaseResultMap")
    @Select("SELECT * FROM t_principal WHERE id=#{id}")
    Principal selectByPrimaryKey(@Param("id") Long id);

}
