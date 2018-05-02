package com.sukidesu.server.service;

import com.sukidesu.common.domain.SeckillOrder;

/**
 * @author weixian.yan
 * @created on 15:55 2018/4/26
 * @description:
 */
public interface SeckillOrderService {

    /**
     * 插入用户秒杀订单
     * @param order
     * @return
     */
    int insert(SeckillOrder order);

    /**
     * 更新订单信息
     * @param order
     * @return
     */
    int update(SeckillOrder order);

    /**
     * 查询订单
     * @param order
     * @return
     */
    SeckillOrder queryOrder(SeckillOrder order);
}
