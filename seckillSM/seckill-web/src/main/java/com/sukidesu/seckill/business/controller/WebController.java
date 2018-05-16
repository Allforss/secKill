package com.sukidesu.seckill.business.controller;

import com.sukidesu.common.common.Constants.Constants;
import com.sukidesu.common.domain.SeckillGoods;
import com.sukidesu.common.dto.Exposer;
import com.sukidesu.common.dto.PageDTO;
import com.sukidesu.common.dto.SeckillExecution;
import com.sukidesu.common.dto.SeckillResult;
import com.sukidesu.seckill.base.common.page.PageList;
import com.sukidesu.seckill.base.model.MessageBean;
import com.sukidesu.seckill.base.shiro.ShiroUtil;
import com.sukidesu.seckill.base.web.BaseController;
import com.sukidesu.seckill.business.redis.GoodsNumberRedis;
import com.sukidesu.seckill.business.service.SeckillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * @author weixian.yan
 * @created on 15:18 2018/5/2
 * @description:
 */
@Slf4j
@Controller
@RequestMapping("/web/seckill")
public class WebController extends BaseController{

    private static final String ORDERBY = Constants.ORDER_BY_CREATETIME_DESC;

    @Autowired
    private GoodsNumberRedis goodsNumberRedis;

    @Autowired
    private SeckillService seckillService;

    @GetMapping("/home")
    public String home(){
        return "home";
    }

    @GetMapping("/detail")
    public String toDetail(Long goodsId, ModelMap model){
        SeckillGoods goods = seckillService.getDetail(goodsId);
        model.addAttribute("goods", goods);
        return "business/seckill/detail";
    }

    @PostMapping("/list")
    @ResponseBody
    public MessageBean pageList(String name, Integer offset, Integer limit){
        log.info("pageList入参：name={},offset={},limit={}",name,offset,limit);
        if(null == offset){
            offset = 0;
        }
        if(null == limit){
            limit = 10;
        }
        SeckillGoods goods = new SeckillGoods(name);
        PageDTO<SeckillGoods> pageDTO = new PageDTO<>(goods, offset, limit, ORDERBY);
        return this.process(() -> {
            PageList<SeckillGoods> page = seckillService.pageList(pageDTO);
            log.info("pageList出参：page={}",page);
            return page;
        });
    }

    @PostMapping("/exposer")
    @ResponseBody
    public SeckillResult<Exposer> exposer(Long goodsId){
        log.info("exposer入参：goodsId={}",goodsId);
        if(null == goodsId){
            return new SeckillResult<Exposer>(false, "商品id为空");
        }
        SeckillResult<Exposer> result = seckillService.exposer(goodsId);
        String userId = ShiroUtil.getCurrentUser().getUserId();
        result.getData().setUserId(userId);
        log.info("exposer出参：result={}",result);
        return result;
    }

    @PostMapping("/execution")
    @ResponseBody
    public SeckillResult<SeckillExecution> execute(Long goodsId, String userId, String md5){
        log.info("execute入参：goodsId={},userId={},md5={}",goodsId,userId,md5);
        if(null == goodsId){
            return new SeckillResult<SeckillExecution>(false, "商品id为空");
        }
        if(null == userId){
            return new SeckillResult<SeckillExecution>(false, "用户id为空");
        }
        if(null == md5){
            return new SeckillResult<SeckillExecution>(false, "md5值为空");
        }
        boolean flag = goodsNumberRedis.reduceGoodsNumber(goodsId);
        if(!flag){
            return new SeckillResult<SeckillExecution>(false,"对不起，该商品已被抢光！");
        }
        SeckillResult<SeckillExecution> result = seckillService.execute(goodsId, userId, md5);
        log.info("execute 出参：result={}",result);
        return result;
    }

    @GetMapping("/time/now")
    @ResponseBody
    public SeckillResult<Long> time(){
        Date now = new Date();
        log.info("now--------{}",now);
        return new SeckillResult<Long>(true, now.getTime());
    }


}
