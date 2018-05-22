package com.sukidesu.seckill.business.redis;

import com.sukidesu.common.common.Constants.Constants;
import com.sukidesu.common.domain.SeckillGoods;
import com.sukidesu.seckill.business.controller.GoodsController;
import com.sun.media.sound.FFT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author weixian.yan
 * @created on 17:17 2018/5/14
 * @description:
 */
@Slf4j
@Component
public class GoodsNumberRedis {

    /*允许进行后续秒杀的调整系数，根据商品数量确定*/
    private static final double GOODS_NUMBER_PARAM = 1.2f;

    private static Object redisLockA = new Object();

    private static Object redisLockB = new Object();

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private GoodsController goodsController;

    private Long number;

    private boolean setGoodsNumber(long goodsId, long number, RedisOperations operations){
        String redisKey = Constants.SeckillRedis.GOOGS_NUMBER + goodsId;
        this.number = number;
        operations.opsForValue().set(redisKey, number);
        operations.expire(redisKey, Constants.SeckillRedis.EXPIRE_TIME, TimeUnit.HOURS);
        return true;
    }

    private Long getGoodsNumber(long goodsId, RedisOperations operations){
        String redisKey = Constants.SeckillRedis.GOOGS_NUMBER + goodsId;
        boolean flag = operations.hasKey(redisKey);
        if(!flag){
            return null;
        }
        Object value = operations.opsForValue().get(redisKey);
        this.number = Long.valueOf(value.toString());
        return number;
    }

    /**
     * 对请求进行限制
     * @param goodsId
     * @param userId
     * @return
     */
    public boolean trafficLimit(long goodsId, String userId){
        log.info("reduceGoodsNumber入参：goodsId={},userId={}",goodsId,userId);
        String redisKey = Constants.SeckillRedis.GOOGS_NUMBER + goodsId;
        String userRedisKey = Constants.SeckillRedis.SECKILL_ALREAD_KILLED + goodsId;
        synchronized (redisLockA){
            //先进行重复秒杀判定
            if (rekillCheck(userId, redisKey, userRedisKey))
                return false;

            //取出当前允许秒杀名额，进行名额限制判定
            number = getGoodsNumber(goodsId, redisTemplate);
            if(null == number){
                ifNull(goodsId, redisTemplate);
            }
            if(number <= 0){
                return false;
            }
        }
        return true;
    }

    /**
     * 对重复秒杀请求进行过滤,若重复则返回true
     * @param userId
     * @param redisKey
     * @param userRedisKey
     * @return
     */
    private boolean rekillCheck(String userId, String redisKey, String userRedisKey) {
        boolean flag = redisTemplate.hasKey(redisKey);
        if(flag){//如果key存在但是set集合也存在则是重复秒杀，直接返回失败
            boolean exist = redisTemplate.opsForSet().isMember(userRedisKey, userId);
            log.info(">>>>>>>> exist={}",exist);
            if(exist){
                return true;
            }
        }
        return false;
    }

    /**
     * 使用redis对允许进行秒杀的用户数进行限制
     * @param goodsId
     * @param userId
     * @return
     */
    public boolean reduceGoodsNumber(long goodsId, String userId){
        log.info("reduceGoodsNumber入参：goodsId={},userId={}",goodsId,userId);
        String userRedisKey = Constants.SeckillRedis.SECKILL_ALREAD_KILLED + goodsId;
        synchronized (redisLockB){
            //取出当前允许秒杀名额，进行名额限制判定
            number = getGoodsNumber(goodsId, redisTemplate);
            if(null == number){
                ifNull(goodsId, redisTemplate);
            }
            if(number <= 0){
                return false;
            } else {
                setGoodsNumber(goodsId, number-1,redisTemplate);
            }
            redisTemplate.opsForSet().add(userRedisKey, userId);
            redisTemplate.expire(userRedisKey, Constants.SeckillRedis.EXPIRE_TIME, TimeUnit.HOURS);
        }
        return true;
    }

    private boolean ifNull(Long goodsId, RedisOperations operations){
        SeckillGoods goods = new SeckillGoods();
        goods.setGoodsId(goodsId);
        goods = goodsController.queryOne(goods);
        int reTries = 3;
        Long num = null;
        if(null == goods){//如果请求被熔断返回null
            while(reTries > 0){
                try {
                    Thread.sleep(200);
                    num = getGoodsNumber(goodsId,operations);
                    if(null != num){
                        this.number = num;
                        return true;
                    }
                } catch (InterruptedException e) {
                    log.error("线程睡眠异常:{}",e.getMessage());
                    e.printStackTrace();
                }
                reTries--;
            }
        } else {
            num = goods.getNumber();
            log.info("---------num before={}",num);
            num = Math.round(num * GOODS_NUMBER_PARAM);
            log.info("---------num={}",num);
            this.number = num;
            setGoodsNumber(goodsId, num, operations);
            return true;
        }
        return false;
    }

}
