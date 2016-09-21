package com.weibangong.message.transfer.service;

import com.weibangong.message.transfer.exception.SequenceException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

/**
 * Created by jianghailong on 16/1/4.
 */
@Data
@Slf4j
public class SequenceService {

    private JedisSentinelPool pool;

    public Long generate(String key) throws SequenceException {
        Long sequence = null;
        Jedis jedis = pool.getResource();
        try {
            sequence = jedis.incr(key);
        } catch (Exception e) {
            throw new SequenceException(e);
        } finally {
            pool.returnResourceObject(jedis);
        }
        return sequence;
    }
}
