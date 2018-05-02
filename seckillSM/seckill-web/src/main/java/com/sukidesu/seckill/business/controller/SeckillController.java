package com.sukidesu.seckill.business.controller;

import com.github.pagehelper.Page;
import com.sukidesu.common.domain.SeckillGoods;
import com.sukidesu.common.dto.Exposer;
import com.sukidesu.common.dto.PageDTO;
import com.sukidesu.common.dto.SeckillExecution;
import com.sukidesu.common.dto.SeckillResult;
import com.sukidesu.seckill.business.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author weixian.yan
 * @created on 15:18 2018/5/2
 * @description:
 */
@Controller
@RequestMapping("/web")
public class SeckillController {

    @Autowired
    private SeckillService seckillService;

    @PostMapping("/list")
    @ResponseBody
    Page<SeckillGoods> pageList(SeckillGoods goods, int offset, int limit){
        PageDTO<SeckillGoods> pageDTO = new PageDTO<>(goods, offset, limit);
        return seckillService.pageList(pageDTO);
    }

    @PostMapping("/exposer")
    @ResponseBody
    SeckillResult<Exposer> exposer(Long goodsId){
        System.out.println("goodsId=" + goodsId);
        return seckillService.exposer(goodsId);
    }

    @PostMapping("/execution")
    @ResponseBody
    SeckillResult<SeckillExecution> execute(Long goodsId, String userId, String md5){
        System.out.println("goodsId=" + goodsId + ",userId=" + userId + ",md5=" + md5);
        return seckillService.execute(goodsId, userId, md5);
    }

    @GetMapping("/time/now")
    @ResponseBody
    public SeckillResult<Long> time(){
        Date now = new Date();
        System.out.println("-------------------now:"+now.toString());
        return new SeckillResult<Long>(true, now.getTime());
    }


}
