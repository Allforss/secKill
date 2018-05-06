package com.sukidesu.seckill.business.controller;

import com.sukidesu.common.common.page.PageList;
import com.sukidesu.common.domain.SeckillGoods;
import com.sukidesu.common.dto.PageDTO;
import com.sukidesu.seckill.base.model.MessageBean;
import com.sukidesu.seckill.base.web.BaseController;
import com.sukidesu.seckill.business.service.GoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author weixian.yan
 * @created on 16:39 2018/5/6
 * @description: 商品控制器
 */
@Slf4j
@RestController
@RequestMapping("/web/goods")
public class GoodsController extends BaseController{

    @Autowired
    private GoodsService goodsService;

    @PostMapping("/insert")
    public MessageBean insertOne(SeckillGoods goods){
        log.info("GoodsController.insertOne 入参：goods={}",goods);
        return goodsService.insertOne(goods);
    }

    @PostMapping("/insertBatch")
    public MessageBean insertBatch(List<SeckillGoods> goodsList){
        log.info("GoodsController.insertBatch 入参：goodsList={}",goodsList);
        return goodsService.insertBatch(goodsList);
    }

    @PostMapping("/update")
    public MessageBean update(SeckillGoods goods){
        log.info("GoodsController.update 入参：goods={}",goods);
        return goodsService.update(goods);
    }

    @PostMapping("/queryOne")
    public SeckillGoods queryOne(SeckillGoods goods){
        log.info("GoodsController.queryOne 入参：goods={}",goods);
        return goodsService.queryOne(goods);
    }

    @PostMapping("/list")
    public MessageBean queryList(SeckillGoods goods, Integer offset, Integer limit){
        log.info("GoodsController.queryList 入参：goods={},offset={},limit={}",goods,offset,limit);
        if(null == offset){
            offset = 0;
        }
        if(null == limit){
            limit = 10;
        }
        PageDTO<SeckillGoods> pageDTO = new PageDTO<SeckillGoods>(goods, offset, limit);
        return this.process(() -> {
            PageList<SeckillGoods> page = goodsService.queryList(pageDTO);
            log.info("pageList出参：page={}",page);
            return page;
        });

    }

}
