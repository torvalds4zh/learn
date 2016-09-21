package com.weibangong.message.transfer.mapper;

import com.weibangong.message.transfer.model.Feedback;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;

public interface FeedbackMapper {

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("INSERT INTO t_feedback (id, tenant_id, created_by_id, created_at, content, app_name, app_version, system_name, system_version, channel, type)\n" +
            "        VALUES (#{id}, #{tenantId}, #{createdById}, #{createdAt}, #{content}, #{appName}, #{appVersion}, #{systemName}, #{systemVersion}, #{channel}, #{type})")
    int insert(Feedback record);

}