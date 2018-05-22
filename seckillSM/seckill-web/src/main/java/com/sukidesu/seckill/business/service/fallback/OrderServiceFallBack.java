package com.sukidesu.seckill.business.service.fallback;

import com.sukidesu.common.common.page.PageList;
import com.sukidesu.common.domain.SeckillOrder;
import com.sukidesu.common.dto.PageDTO;
import com.sukidesu.common.exception.BizException;
import com.sukidesu.seckill.base.model.MessageBean;
import com.sukidesu.seckill.business.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author weixian.yan
 * @created on 22:31 2018/5/19
 * @description:
 */
@Slf4j
@Component
public class OrderServiceFallBack implements OrderService {

    @Override
    public MessageBean updateOrder(SeckillOrder order) {
        log.info("OrderService updateOrder请求超时，已熔断");
        return new MessageBean(new BizException("请求超时"));
    }

    @Override
    public PageList<SeckillOrder> queryList(PageDTO<SeckillOrder> pageDTO) {
        log.info("OrderService queryList请求超时，已熔断");
        return new PageList<SeckillOrder>(0,null);
    }
}
