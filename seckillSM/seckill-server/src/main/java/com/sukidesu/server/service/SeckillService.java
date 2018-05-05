package com.sukidesu.server.service;

import com.github.pagehelper.Page;
import com.sukidesu.common.common.page.PageList;
import com.sukidesu.common.domain.SeckillGoods;
import com.sukidesu.common.domain.SeckillOrder;
import com.sukidesu.common.dto.Exposer;
import com.sukidesu.common.dto.SeckillExecution;
import com.sukidesu.common.exception.RepeatSeckillException;
import com.sukidesu.common.exception.SeckillCloseException;
import com.sukidesu.common.exception.SeckillException;

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

    /**
     * 通过存储过程执行秒杀操作
     * @param order
     * @param md5
     * @return
     * @throws RepeatSeckillException
     * @throws SeckillCloseException
     * @throws SeckillException
     */
    SeckillExecution executeSeckillByProcedure(SeckillOrder order, String md5)
            throws RepeatSeckillException,SeckillCloseException,SeckillException;
}
