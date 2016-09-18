package com.haizhi.mq.message;

public interface MessageType {

    int None = -1;

    int Form = 1;

    //100开头，联表 合表 聚合表 查询表等数据视图数据变化Message

    int JoinView = 103;

    int AggregationView = 104;

    int QueryView = 105;

    //200开头 form trigger类型
    int Trigger = 200;

    //bdp sync
    int BdpSync = 300;

}