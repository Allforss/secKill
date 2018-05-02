package com.sukidesu.server.controller;

import com.github.pagehelper.Page;
import com.sukidesu.server.common.Constants.Constants;
import com.sukidesu.server.common.enums.OrderStateEnum;
import com.sukidesu.server.common.enums.SeckillStateEnum;
import com.sukidesu.server.domain.SeckillGoods;
import com.sukidesu.server.domain.SeckillOrder;
import com.sukidesu.server.dto.Exposer;
import com.sukidesu.server.dto.SeckillExecution;
import com.sukidesu.server.dto.SeckillResult;
import com.sukidesu.server.exception.RepeatSeckillException;
import com.sukidesu.server.exception.SeckillCloseException;
import com.sukidesu.server.service.SeckillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author weixian.yan
 * @created on 16:37 2018/4/29
 * @description: 秒杀控制器
 */
@RestController
@RequestMapping("seckill")
@Slf4j
public class SeckillController {

    private static final String ORDERBY = Constants.ORDER_BY_CREATETIME_DESC;

    @Autowired
    private SeckillService seckillService;

    @PostMapping("/list")
    public Page<SeckillGoods> pageList(SeckillGoods goods, int offset, int limit){
        Page<SeckillGoods> page = seckillService.getSeckillList(goods, offset, limit, ORDERBY);
        return page;
    }

    @PostMapping("/exposer")
    public SeckillResult<Exposer> exposer(Long goodsId){
        SeckillResult<Exposer> result = null;
        try {
            Exposer exposer = seckillService.exposeSeckillUrl(goodsId);
            result = new SeckillResult<Exposer>(true, exposer);

        } catch (Exception e) {
            log.error("e{}", e.getMessage());
            result = new SeckillResult<Exposer>(false, e.getMessage());
        }
        return result;
    }

    @PostMapping("/execution")
    public SeckillResult<SeckillExecution> execute(Long goodsId, String userId, String md5){
        try {
            SeckillOrder order = new SeckillOrder();
            order.setGoodsId(goodsId);
            order.setUserId(userId);
            order.setCreateTime(LocalDateTime.now());
            order.setUpdateTime(LocalDateTime.now());
            order.setOrderState(OrderStateEnum.SUCCESS.getState());
            SeckillExecution  execution = seckillService.executeSeckill(order, md5);
            return new SeckillResult<SeckillExecution>(true, execution);
        } catch (RepeatSeckillException e) {
            SeckillExecution execution = new SeckillExecution(goodsId, SeckillStateEnum.REPEAT_KILL);
            return  new SeckillResult<SeckillExecution>(true, execution);
        } catch (SeckillCloseException e) {
            SeckillExecution execution = new SeckillExecution(goodsId, SeckillStateEnum.END);
            return new SeckillResult<SeckillExecution>(true, execution);
        } catch (Exception e) {
            SeckillExecution execution = new SeckillExecution(goodsId, SeckillStateEnum.INNER_ERROR);
            return new SeckillResult<SeckillExecution>(true, execution);
        }
    }

    @GetMapping("/time/now")
    public SeckillResult<Long> time(){
        Date now = new Date();
        System.out.println("-------------------now:"+now.toString());
        return new SeckillResult<Long>(true, now.getTime());
    }

}
