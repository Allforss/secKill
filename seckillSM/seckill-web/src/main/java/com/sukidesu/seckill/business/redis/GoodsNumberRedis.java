package com.sukidesu.seckill.business.redis;

import com.sukidesu.common.common.Constants.Constants;
import com.sukidesu.common.domain.SeckillGoods;
import com.sukidesu.seckill.business.controller.GoodsController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Component;

/**
 * @author weixian.yan
 * @created on 17:17 2018/5/14
 * @description:
 */
@Component
public class GoodsNumberRedis {

    /*允许进行后续秒杀的调整系数，根据商品数量确定*/
    private static final double GOODS_NUMBER_PARAM = 1.2f;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private GoodsController goodsController;

    private boolean setGoodsNumber(long goodsId, long number, RedisOperations operations){
        String redisKey = Constants.SeckillRedis.GOOGS_NUMBER + goodsId;
        operations.opsForValue().set(redisKey, number);
        return true;
    }

    private Long getGoodsNumber(long goodsId, RedisOperations operations){
        String redisKey = Constants.SeckillRedis.GOOGS_NUMBER + goodsId;
        Object value = operations.opsForValue().get(redisKey);
        if(null == value){
            return null;
        }
        Long number = Long.valueOf(value.toString());
        return number;
    }

    public boolean reduceGoodsNumber(long goodsId){
        String redisKey = Constants.SeckillRedis.GOOGS_NUMBER + goodsId;
        Long num = getGoodsNumber(goodsId, redisTemplate);
        if(null == num){
            ifNull(goodsId, num, redisTemplate);
        }
        if(num <= 0){
            return false;
        }
        SessionCallback callback = new SessionCallback() {

            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                operations.multi();
                Long number = getGoodsNumber(goodsId, operations);
                if(null == number){
                   number = ifNull(goodsId, number, operations);
                }
                if(number <= 0){
                    operations.opsForValue().set(redisKey, number-1);
                }
                Object result = operations.exec();
                return result;
            }
        };
        redisTemplate.execute(callback);
        return true;
    }

    private Long ifNull(Long goodsId, Long num, RedisOperations operations){
        SeckillGoods goods = new SeckillGoods();
        goods.setGoodsId(goodsId);
        goods = goodsController.queryOne(goods);
        num = goods.getNumber();
        num = Math.round(num * GOODS_NUMBER_PARAM);
        setGoodsNumber(goodsId, num, operations);
        return num;
    }

}
