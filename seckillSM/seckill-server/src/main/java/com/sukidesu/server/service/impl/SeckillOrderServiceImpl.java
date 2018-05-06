package com.sukidesu.server.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sukidesu.common.domain.MessageBean;
import com.sukidesu.common.domain.SeckillOrder;
import com.sukidesu.common.exception.BizException;
import com.sukidesu.server.mapper.SeckillOrderMapper;
import com.sukidesu.server.service.SeckillOrderService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public MessageBean update(SeckillOrder order) {
        int rowCount = orderMapper.update(order);
        if(rowCount <= 0){
            return new MessageBean(new BizException("更新订单信息失败"));
        }
        order = orderMapper.queryOrder(order);
        return new MessageBean(order);
    }

    @Override
    public SeckillOrder queryOrder(SeckillOrder order) {
        return orderMapper.queryOrder(order);
    }

    @Override
    public Page<SeckillOrder> queryList(SeckillOrder order, int offset, int limit, String orderBy) {
        return PageHelper.offsetPage(offset, limit)
                .setOrderBy(orderBy)
                .doSelectPage(() -> orderMapper.queryList(order));
    }
}
