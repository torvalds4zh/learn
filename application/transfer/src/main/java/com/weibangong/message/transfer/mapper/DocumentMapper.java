package com.weibangong.message.transfer.mapper;

import com.weibangong.message.transfer.model.Document;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * Created by jianghailong on 16/1/4.
 */
public interface DocumentMapper {

    int insert(Document document);

    @Update("UPDATE t_document SET status=1 WHERE carrier_id=#{id} and carrier_type=0")
    int cancel(@Param("id") Long id);

}
