package com.sukidesu.seckill.business.controller;

import com.sukidesu.common.common.Constants.Constants;
import com.sukidesu.common.common.page.PageList;
import com.sukidesu.common.domain.SeckillGoods;
import com.sukidesu.common.dto.PageDTO;
import com.sukidesu.seckill.base.model.MessageBean;
import com.sukidesu.seckill.base.web.BaseController;
import com.sukidesu.seckill.business.service.GoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author weixian.yan
 * @created on 16:39 2018/5/6
 * @description: 商品控制器
 */
@Slf4j
@Controller
@RequestMapping("/web/goods")
public class GoodsController extends BaseController{

    private static final String ORDERBY = Constants.ORDER_BY_GOODSID_ASC;

    @Autowired
    private GoodsService goodsService;

    @GetMapping("/list")
    public String toAdminGoodsList(){
        return "business/goods/list";
    }

    @GetMapping("/edit/{goodsId}")
    public String toEdit(@PathVariable("goodsId") Long goodsId, Model model){
        if(null == goodsId){
            return "redirect:/web/goods/list";
        }
        SeckillGoods goods = new SeckillGoods();
        goods.setGoodsId(goodsId);
        goods = goodsService.queryOne(goods);
        model.addAttribute("goods",goods);
        return "business/goods/edit";
    }

    @GetMapping("/add")
    public String toAdd(){
        return "business/goods/add";
    }

    @PostMapping("/insert")
    @ResponseBody
    public MessageBean insertOne(SeckillGoods goods){
        log.info("GoodsController.insertOne 入参：goods={}",goods);
        goods.setPrice(goods.getPrice() * 100);//转化为分存储
        goods.setCreateTime(LocalDateTime.now());
        goods.setUpdateTime(LocalDateTime.now());
        MessageBean result = goodsService.insertOne(goods);
        log.info("result={}",result);
        return result;
    }

    @PostMapping("/insertBatch")
    @ResponseBody
    public MessageBean insertBatch(List<SeckillGoods> goodsList){
        log.info("GoodsController.insertBatch 入参：goodsList={}",goodsList);
        return goodsService.insertBatch(goodsList);
    }

    @PostMapping("/update")
    @ResponseBody
    public MessageBean update(SeckillGoods goods){
        log.info("GoodsController.update 入参：goods={}",goods);
        MessageBean result = goodsService.update(goods);
        return result;
    }

    @PostMapping("/queryOne")
    @ResponseBody
    public SeckillGoods queryOne(SeckillGoods goods){
        log.info("GoodsController.queryOne 入参：goods={}",goods);
        return goodsService.queryOne(goods);
    }

    @PostMapping("/list")
    @ResponseBody
    public MessageBean queryList(SeckillGoods goods, Integer offset, Integer limit){
        log.info("GoodsController.queryList 入参：goods={},offset={},limit={}",goods,offset,limit);
        if(null == offset){
            offset = 0;
        }
        if(null == limit){
            limit = 10;
        }
        PageDTO<SeckillGoods> pageDTO = new PageDTO<SeckillGoods>(goods, offset, limit, ORDERBY);
        return this.process(() -> {
            PageList<SeckillGoods> page = goodsService.queryList(pageDTO);
            log.info("pageList出参：page={}",page);
            return page;
        });

    }

}
