package com.sukidesu.seckill.business.controller;

import com.sukidesu.common.common.Constants.Constants;
import com.sukidesu.common.common.page.PageList;
import com.sukidesu.common.domain.SeckillGoods;
import com.sukidesu.common.domain.SeckillOrder;
import com.sukidesu.common.dto.PageDTO;
import com.sukidesu.seckill.base.model.MessageBean;
import com.sukidesu.seckill.base.shiro.ShiroUtil;
import com.sukidesu.seckill.base.web.BaseController;
import com.sukidesu.seckill.business.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author weixian.yan
 * @created on 14:06 2018/5/6
 * @description: 订单控制器
 */
@Slf4j
@Controller
@RequestMapping("/web/order")
public class OrderController extends BaseController{

    private static final String ORDERBY = Constants.ORDER_BY_SO_CREATETIME_DESC;

    @Autowired
    private OrderService orderService;

    @GetMapping("/list")
    public String toOrderList(){
        return "business/order/list";
    }

    @GetMapping("/admin/list")
    public String toAdminOrderList(){
        return "business/order/adminList";
    }

    @PostMapping("/user/queryList")
    @ResponseBody
    public MessageBean queryList(String name, Integer offset, Integer limit){
        log.info("OrderController.queryList入参：name={},offset={},limit={}",name, offset, limit);
        if(null == offset){
            offset = 0;
        }
        if(null == limit){
            limit = 10;
        }
        SeckillOrder order = new SeckillOrder();
        SeckillGoods goods = new SeckillGoods();
        String userId = ShiroUtil.getCurrentUser().getUserId();
        goods.setName(name);
        order.setGoods(goods);
        order.setUserId(userId);
        PageDTO<SeckillOrder> pageDTO = new PageDTO<>(order, offset, limit, ORDERBY);
        return this.process(() -> {
            PageList<SeckillOrder> page = orderService.queryList(pageDTO);
            log.info("pageList出参：page={}",page);
            return page;
        });
    }

    @PostMapping("/admin/queryList")
    @ResponseBody
    public MessageBean queryListByAdmin(String name, Integer offset, Integer limit){
        log.info("OrderController.queryList入参：name={},offset={},limit={}",name, offset, limit);
        if(null == offset){
            offset = 0;
        }
        if(null == limit){
            limit = 10;
        }
        SeckillOrder order = new SeckillOrder();
        SeckillGoods goods = new SeckillGoods();
        goods.setName(name);
        order.setGoods(goods);
        PageDTO<SeckillOrder> pageDTO = new PageDTO<>(order, offset, limit, ORDERBY);
        return this.process(() -> {
            PageList<SeckillOrder> page = orderService.queryList(pageDTO);
            log.info("pageList出参：page={}",page);
            return page;
        });
    }

    @PostMapping("/update")
    @ResponseBody
    public MessageBean updateOrder(SeckillOrder order){
        log.info("OrderController.updateOrder入参: order={}",order);
        MessageBean result = orderService.updateOrder(order);
        log.info("OrderController.updateOrder出参：result={}",result);
        return result;
    }
}
