package com.sukidesu.seckill.business.service;

import com.sukidesu.common.common.page.PageList;
import com.sukidesu.common.domain.SeckillOrder;
import com.sukidesu.common.dto.PageDTO;
import com.sukidesu.seckill.base.model.MessageBean;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author weixian.yan
 * @created on 14:07 2018/5/6
 * @description: 订单服务Feign接口
 */
@FeignClient(value = "seckill-server")
public interface OrderService {

    @RequestMapping(value = "/order/update", method = RequestMethod.POST)
    MessageBean updateOrder(@RequestBody SeckillOrder order);

    @RequestMapping(value = "/order/list", method = RequestMethod.POST)
    PageList<SeckillOrder> queryList(@RequestBody PageDTO<SeckillOrder> pageDTO);


}
