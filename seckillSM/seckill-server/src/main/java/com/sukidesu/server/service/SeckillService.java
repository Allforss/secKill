package com.sukidesu.server.service;

import com.github.pagehelper.Page;
import com.sukidesu.server.domain.SeckillGoods;
import com.sukidesu.server.domain.SeckillOrder;
import com.sukidesu.server.dto.Exposer;
import com.sukidesu.server.dto.SeckillExecution;
import com.sukidesu.server.exception.RepeatSeckillException;
import com.sukidesu.server.exception.SeckillCloseException;
import com.sukidesu.server.exception.SeckillException;

/**
 * @author weixian.yan
 * @created on 13:25 2018/4/29
 * @description:
 */
public interface SeckillService {

    /**
     * 获取商品列表
     * @return
     */
    Page<SeckillGoods> getSeckillList(SeckillGoods goods, int offset, int limit, String orderBy);

    /**
     * 根据商品id获取商品信息
     * @param goodsId
     * @return
     */
    SeckillGoods getById(long goodsId);

    /**
     * 暴露秒杀URL
     * @param goodsId
     * @return
     */
    Exposer exposeSeckillUrl(long goodsId);

    /**
     * 执行秒杀
     * @param order
     * @param md5
     * @return
     */
    SeckillExecution executeSeckill(SeckillOrder order, String md5)
            throws RepeatSeckillException,SeckillCloseException,SeckillException;

}
