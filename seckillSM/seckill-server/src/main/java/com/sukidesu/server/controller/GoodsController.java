package com.sukidesu.server.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.sukidesu.common.common.Constants.Constants;
import com.sukidesu.common.common.page.PageList;
import com.sukidesu.common.domain.MessageBean;
import com.sukidesu.common.domain.SeckillGoods;
import com.sukidesu.common.dto.PageDTO;
import com.sukidesu.server.service.SeckillGoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author weixian.yan
 * @created on 16:11 2018/5/6
 * @description:
 */
@Slf4j
@RestController
@RequestMapping("/goods")
public class GoodsController {

    private static final String ORDERBY = Constants.ORDER_BY_CREATETIME_DESC;

    @Autowired
    private SeckillGoodsService goodsService;

    @PostMapping("/insert")
    public MessageBean insert(@RequestBody SeckillGoods goods){
        log.info("GoodsController.insert 入参:goods={}",goods);
        return goodsService.insertOne(goods);
    }

    @PostMapping("/insertBatch")
    public MessageBean insertBatch(@RequestBody List<SeckillGoods> goodsList){
        log.info("GoodsController.insertBatch 入参:goodsList={}",goodsList);
        return goodsService.insertBatch(goodsList);
    }

    @PostMapping("/update")
    public MessageBean update(@RequestBody SeckillGoods goods){
        log.info("GoodsController.update 入参:goods={}",goods);
        return goodsService.update(goods);
    }

    @PostMapping("/queryOne")
    public SeckillGoods queryOne(@RequestBody SeckillGoods goods){
        log.info("GoodsController.queryOne 入参:goods={}",goods);
        return goodsService.selectOne(goods);
    }

    @PostMapping("/list")
    public PageList<SeckillGoods> queryList(@RequestBody PageDTO<SeckillGoods> pageDTO){
        log.info("GoodsController.queryList 入参:pageDTO={}",pageDTO);
        SeckillGoods goods = pageDTO.getModel();
        int offset = pageDTO.getOffset();
        int limit = pageDTO.getLimit();
        Page<SeckillGoods> page = goodsService.selectList(goods, offset, limit, ORDERBY);
        log.info("出参：page={}",page);
        PageList<SeckillGoods> result = new PageList<SeckillGoods>(page.getTotal(), page.getResult());
        log.info("JSON序列化result={}", JSON.toJSONString(result));
        return result;
    }

}
