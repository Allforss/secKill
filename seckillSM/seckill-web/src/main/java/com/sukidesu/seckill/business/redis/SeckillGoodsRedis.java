package com.sukidesu.seckill.business.redis;

import com.alibaba.fastjson.JSON;
import com.sukidesu.common.common.Constants.Constants;
import com.sukidesu.common.domain.SeckillGoods;
import com.sukidesu.common.exception.RedisException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author weixian.yan
 * @created on 14:44 2018/4/29
 * @description:
 */
@Component
public class SeckillGoodsRedis {

    @Autowired
    private RedisTemplate redisTemplate;

    public boolean putSeckillGoods(SeckillGoods goods) throws RedisException{
        if(null == goods || null == goods.getGoodsId()){
            throw new RedisException("redis key 相关信息（goodsId）不能为空");
        }
        String redisKey = Constants.SeckillRedis.SECKILL_PREFIX + goods.getGoodsId();
        redisTemplate.opsForValue().set(redisKey, JSON.toJSONString(goods));
        redisTemplate.expire(redisKey, Constants.SeckillRedis.EXPIRE_TIME, TimeUnit.HOURS);
        return true;
    }

    public SeckillGoods getSeckillGoods(long goodsId) {
        String redisKey = Constants.SeckillRedis.SECKILL_PREFIX + goodsId;
        boolean flag = redisTemplate.hasKey(redisKey);
        if(!flag){
            return null;
        }
        String jsonString = (String)redisTemplate.opsForValue().get(redisKey);
        return JSON.parseObject(jsonString, SeckillGoods.class);
    }
}
