package com.sukidesu.server.service.impl;

import com.sukidesu.server.domain.SeckillOrder;
import com.sukidesu.server.mapper.SeckillOrderMapper;
import com.sukidesu.server.service.SeckillOrderService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author weixian.yan
 * @created on 16:03 2018/4/26
 * @description: 订单服务类
 */
@Log4j
@Service
public class SeckillOrderServiceImpl implements SeckillOrderService {

    @Autowired
    private SeckillOrderMapper orderMapper;

    @Override
    public int insert(SeckillOrder order) {
        return orderMapper.insert(order);
    }

    @Override
    public int update(SeckillOrder order) {
        return orderMapper.update(order);
    }

    @Override
    public SeckillOrder queryOrder(SeckillOrder order) {
        return orderMapper.queryOrder(order);
    }
}
