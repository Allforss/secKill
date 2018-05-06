package com.sukidesu.server.service;

import com.github.pagehelper.Page;
import com.sukidesu.common.domain.MessageBean;
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
    MessageBean update(SeckillOrder order);

    /**
     * 查询订单
     * @param order
     * @return
     */
    SeckillOrder queryOrder(SeckillOrder order);

    /**
     * 分页查询订单集合
     * @param order
     * @param offset
     * @param limit
     * @return
     */
    Page<SeckillOrder> queryList(SeckillOrder order, int offset, int limit, String orderBy);
}
