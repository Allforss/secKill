package com.sukidesu.server.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.sukidesu.common.common.Constants.Constants;
import com.sukidesu.common.common.enums.OrderStateEnum;
import com.sukidesu.common.common.enums.SeckillStateEnum;
import com.sukidesu.common.common.page.PageList;
import com.sukidesu.common.common.utils.IdGenerator;
import com.sukidesu.common.domain.SeckillGoods;
import com.sukidesu.common.domain.SeckillOrder;
import com.sukidesu.common.dto.Exposer;
import com.sukidesu.common.dto.PageDTO;
import com.sukidesu.common.dto.SeckillExecution;
import com.sukidesu.common.dto.SeckillResult;
import com.sukidesu.common.exception.RepeatSeckillException;
import com.sukidesu.common.exception.SeckillCloseException;
import com.sukidesu.server.service.SeckillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * @author weixian.yan
 * @created on 16:37 2018/4/29
 * @description: 秒杀控制器
 */
@RestController
@RequestMapping("/seckill")
@Slf4j
public class SeckillController {

    private static final String ORDERBY = Constants.ORDER_BY_CREATETIME_DESC;

    @Autowired
    private SeckillService seckillService;

    @Autowired
    private IdGenerator idGenerator;

    @PostMapping("/list")
    public PageList<SeckillGoods> pageList(@RequestBody PageDTO<SeckillGoods> pageDTO){
        log.info("入参 pageDTO={}",pageDTO);
        SeckillGoods goods = pageDTO.getModel();
        int offset = pageDTO.getOffset();
        int limit = pageDTO.getLimit();
        Page<SeckillGoods> page = seckillService.getSeckillList(goods, offset, limit, ORDERBY);
        log.info("出参：page={}",page);
        PageList<SeckillGoods> result = new PageList<SeckillGoods>(page.getTotal(), page.getResult());
        log.info("JSON序列化result={}", JSON.toJSONString(result));
        return result;
    }

    @GetMapping("/detail")
    public SeckillGoods getDetail(Long goodsId){
        if(null == goodsId){
            return null;
        }
        log.info("入参 goodsId={}",goodsId);
        SeckillGoods goods = seckillService.getById(goodsId);
        log.info("出参：goods={}",goods);
        return goods;
    }

    @PostMapping("/exposer")
    public SeckillResult<Exposer> exposer(@RequestBody Long goodsId){
        log.info("入参 goodsId={}",goodsId);
        SeckillResult<Exposer> result = null;
        try {
            Exposer exposer = seckillService.exposeSeckillUrl(goodsId);
            System.out.println("exposer=" + exposer);
            result = new SeckillResult<Exposer>(true, exposer);

        } catch (Exception e) {
            log.error("e{}", e.getMessage());
            result = new SeckillResult<Exposer>(false, e.getMessage());
        }
        log.info("出参：result={}",result);
        return result;
    }

    @GetMapping("/execution")
    public SeckillResult<SeckillExecution> execute(@RequestParam("goodsId") Long goodsId,
                                                   @RequestParam("userId") String userId,
                                                   @RequestParam("md5") String md5){
        log.info("入参 goodsId={},userId={},md5={}",goodsId, userId, md5);
        try {
            SeckillOrder order = new SeckillOrder();
            order.setOrderId(idGenerator.generateOrderId());
            order.setGoodsId(goodsId);
            order.setUserId(userId);
            order.setCreateTime(LocalDateTime.now());
            order.setUpdateTime(LocalDateTime.now());
            order.setOrderState(OrderStateEnum.SUCCESS.getState());
            SeckillExecution  execution = seckillService.executeSeckill(order, md5);
            log.info("出参 execution={}",execution);
            return new SeckillResult<SeckillExecution>(true, execution);
        } catch (RepeatSeckillException e) {
            log.info("executeByProcedure 异常：{}",e.getMessage());
            SeckillExecution execution = new SeckillExecution(goodsId, SeckillStateEnum.REPEAT_KILL);
            return  new SeckillResult<SeckillExecution>(true, execution);
        } catch (SeckillCloseException e) {
            log.info("executeByProcedure 异常：{}",e.getMessage());
            SeckillExecution execution = new SeckillExecution(goodsId, SeckillStateEnum.END);
            return new SeckillResult<SeckillExecution>(true, execution);
        } catch (Exception e) {
            log.info("executeByProcedure 异常：{}",e.getMessage());
            SeckillExecution execution = new SeckillExecution(goodsId, SeckillStateEnum.INNER_ERROR);
            return new SeckillResult<SeckillExecution>(true, execution);
        }
    }

    @GetMapping("/execution/procedure")
    public SeckillResult<SeckillExecution> executeByProcedure(@RequestParam("goodsId") Long goodsId,
                                                   @RequestParam("userId") String userId,
                                                   @RequestParam("md5") String md5){
        log.info("入参 goodsId={},userId={},md5={}",goodsId, userId, md5);
        try {
            SeckillOrder order = new SeckillOrder();
            order.setOrderId(idGenerator.generateOrderId());
            order.setGoodsId(goodsId);
            order.setUserId(userId);
            order.setCreateTime(LocalDateTime.now());
            order.setUpdateTime(LocalDateTime.now());
            order.setOrderState(OrderStateEnum.SUCCESS.getState());
            SeckillExecution execution = seckillService.executeSeckillByProcedure(order, md5);
            log.info("出参 execution={}",execution);
            return new SeckillResult<SeckillExecution>(true, execution);
        } catch (RepeatSeckillException e) {
            log.info("executeByProcedure 异常：{}",e.getMessage());
            SeckillExecution execution = new SeckillExecution(goodsId, SeckillStateEnum.REPEAT_KILL);
            return  new SeckillResult<SeckillExecution>(true, execution);
        } catch (SeckillCloseException e) {
            log.info("executeByProcedure 异常：{}",e.getMessage());
            SeckillExecution execution = new SeckillExecution(goodsId, SeckillStateEnum.END);
            return new SeckillResult<SeckillExecution>(true, execution);
        } catch (Exception e) {
            log.info("executeByProcedure 异常：{}",e.getMessage());
            SeckillExecution execution = new SeckillExecution(goodsId, SeckillStateEnum.INNER_ERROR);
            return new SeckillResult<SeckillExecution>(true, execution);
        }
    }

}
