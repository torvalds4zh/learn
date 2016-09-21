package com.weibangong.cache;

/**
 * Created by zhangping on 16/5/7.
 */
public interface IdGenerator {

    Long generateByJedis(IdGeneratorType type);

}
