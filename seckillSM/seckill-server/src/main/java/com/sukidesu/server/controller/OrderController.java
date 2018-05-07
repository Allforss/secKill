package com.sukidesu.server.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.sukidesu.common.common.Constants.Constants;
import com.sukidesu.common.common.page.PageList;
import com.sukidesu.common.domain.MessageBean;
import com.sukidesu.common.domain.SeckillGoods;
import com.sukidesu.common.domain.SeckillOrder;
import com.sukidesu.common.dto.PageDTO;
import com.sukidesu.server.service.SeckillOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author weixian.yan
 * @created on 14:08 2018/5/6
 * @description:
 */
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private SeckillOrderService orderService;

    @PostMapping("/list")
    public PageList<SeckillOrder> queryList(@RequestBody PageDTO<SeckillOrder> pageDTO){
        log.info("入参 pageDTO={}",pageDTO);
        SeckillOrder order = pageDTO.getModel();
        int offset = pageDTO.getOffset();
        int limit = pageDTO.getLimit();
        String orderBy = pageDTO.getOrderBy();
        Page<SeckillOrder> page = orderService.queryList(order, offset, limit, orderBy);
        log.info("出参：SeckillOrder={}",page.getResult());
        PageList<SeckillOrder> result = new PageList<>(page.getTotal(), page.getResult());
        log.info("JSON序列化result={}", JSON.toJSONString(result));
        return result;
    }

    @PostMapping("/update")
    public MessageBean updateOrder(@RequestBody SeckillOrder order){
        log.info("入参 order={}",order);
        return orderService.update(order);
    }

}
