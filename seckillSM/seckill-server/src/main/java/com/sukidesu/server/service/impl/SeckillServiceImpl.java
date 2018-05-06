package com.sukidesu.server.service.impl;

import com.github.pagehelper.Page;
import com.sukidesu.common.common.enums.SeckillStateEnum;
import com.sukidesu.common.common.page.PageList;
import com.sukidesu.common.common.utils.MD5Util;
import com.sukidesu.common.domain.SeckillGoods;
import com.sukidesu.common.domain.SeckillOrder;
import com.sukidesu.common.dto.Exposer;
import com.sukidesu.common.dto.SeckillExecution;
import com.sukidesu.common.exception.RepeatSeckillException;
import com.sukidesu.common.exception.SeckillCloseException;
import com.sukidesu.common.exception.SeckillException;
import com.sukidesu.server.redis.SeckillGoodsRedis;
import com.sukidesu.server.service.SeckillGoodsService;
import com.sukidesu.server.service.SeckillOrderService;
import com.sukidesu.server.service.SeckillService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author weixian.yan
 * @created on 13:50 2018/4/29
 * @description: 秒杀相关服务类
 */
@Service
@Slf4j
public class SeckillServiceImpl implements SeckillService {

    @Autowired
    private SeckillOrderService orderService;

    @Autowired
    private SeckillGoodsService goodsService;

    @Autowired
    private SeckillGoodsRedis goodsRedis;

    @Override
    public Page<SeckillGoods> getSeckillList(SeckillGoods goods, int offset,
                                                 int limit, String orderBy) {
        return goodsService.selectList(goods,offset,limit,orderBy);
    }

    @Override
    public SeckillGoods getById(long goodsId) {
        return goodsService.queryById(goodsId);
    }

    @Override
    public Exposer exposeSeckillUrl(long goodsId) {
        log.info("enter exposeSeckillUrl,入参：goodsId={}",goodsId);
        SeckillGoods seckillGoods = goodsRedis.getSeckillGoods(goodsId);
        if(null == seckillGoods){
            seckillGoods = goodsService.queryById(goodsId);
            if (null == seckillGoods) {
                return new Exposer(false, goodsId);
            } else {
                goodsRedis.putSeckillGoods(seckillGoods);
            }
        }
        Date nowTime = new Date();
        Date startTime = seckillGoods.getStartTime();
        Date endTime = seckillGoods.getEndTime();
        if(nowTime.getTime() < startTime.getTime() || nowTime.getTime() > endTime.getTime()){
            return new Exposer(false, goodsId, nowTime.getTime(),
                    startTime.getTime(), endTime.getTime());
        }
        String md5 = MD5Util.getMD5(goodsId);
        System.out.println("MD5=" + md5);
        Exposer exposer = new Exposer(true, md5, goodsId, nowTime.getTime(),
                startTime.getTime(), endTime.getTime());
        log.info("exposeSeckillUrl出参：exposer={}",exposer);
        return exposer;
    }

    @Override
    @Transactional
    public SeckillExecution executeSeckill(SeckillOrder order, String md5)
            throws SeckillException{
        if (md5 == null || !md5.equals(MD5Util.getMD5(order.getGoodsId()))) {
            throw new SeckillException("秒杀信息被篡改");
        }
        // 执行秒杀逻辑：减库存+增加秒杀成功记录
        Date killTime = new Date();
        long goodsId = order.getGoodsId();

        try{
            int rowCount = goodsService.reduceNumberById(goodsId, killTime);
            if(rowCount <=0 ){
                throw new SeckillCloseException("该商品秒杀已关闭");
            } else {
                int insertCount = orderService.insert(order);
                if(insertCount <=0 ){//重复秒杀
                    throw new RepeatSeckillException("对不起，您不能重复秒杀");
                } else { //秒杀成功
                    logSeckillTime(killTime);
                    return new SeckillExecution(goodsId, SeckillStateEnum.SUCCESS, order);
                }
            }

        } catch (RepeatSeckillException e1){
            log.error("重复秒杀异常:{}",e1);
            throw e1;
        } catch (SeckillCloseException e2){
            log.error("秒杀已经关闭：{}",e2);
            throw e2;
        } catch (Exception e3){
            log.error("系统内部异常：{}",e3);
            throw new SeckillException("系统内部异常");
        }
    }

    @Override
    public SeckillExecution executeSeckillByProcedure(SeckillOrder order, String md5)
            throws SeckillException {
        if (md5 == null || !md5.equals(MD5Util.getMD5(order.getGoodsId()))) {
            throw new SeckillException("秒杀信息被篡改");
        }
        // 执行秒杀逻辑：减库存+增加秒杀成功记录
        Date killTime = new Date();
        long goodsId = order.getGoodsId();
        String userId = order.getUserId();
        Map<String, Object> params = new HashMap<String,Object>();
        params.put("orderId",order.getOrderId());
        params.put("goodsId", goodsId);
        params.put("userId", userId);
        params.put("killTime", killTime);
        params.put("result", null);
        try {
            goodsService.killByProcedure(params);
            // 获取result
            int result = MapUtils.getInteger(params, "result", -2);
            if (result == 1) {
                logSeckillTime(killTime);
                return new SeckillExecution(goodsId, SeckillStateEnum.SUCCESS, order);
            } else {
                return new SeckillExecution(goodsId, SeckillStateEnum.stateOf(result));
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new SeckillExecution(goodsId, SeckillStateEnum.INNER_ERROR);
        }
    }

    private void logSeckillTime(Date startKillTime){
        Date endKillTime = new Date();
        log.info("秒杀事务耗时（ms）：{}",endKillTime.getTime() - startKillTime.getTime());
    }

}
