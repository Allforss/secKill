package com.sukidesu.seckill.business.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import com.sukidesu.common.domain.SeckillGoods;
import com.sukidesu.common.dto.Exposer;
import com.sukidesu.common.dto.PageDTO;
import com.sukidesu.common.dto.SeckillExecution;
import com.sukidesu.common.dto.SeckillResult;
import com.sukidesu.seckill.base.common.page.PageList;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

/**
 * @author weixian.yan
 * @created on 15:16 2018/5/2
 * @description: 秒杀服务Feign接口
 */
@FeignClient(value = "seckill-server")
public interface SeckillService {

    @CacheResult
    @HystrixCommand(fallbackMethod = "pageListFallBack")
    @RequestMapping(value = "/seckill/list",method = RequestMethod.POST)
    PageList<SeckillGoods> pageList(@RequestBody PageDTO<SeckillGoods> pageDTO);

    @CacheResult
    @HystrixCommand(fallbackMethod = "getDetailFallBack")
    @RequestMapping(value = "/seckill/detail",method = RequestMethod.GET)
    SeckillGoods getDetail(@RequestParam("goodsId") Long goodsId);

    @CacheResult
    @HystrixCommand(fallbackMethod = "exposerFallback")
    @RequestMapping(value = "/seckill/exposer",method = RequestMethod.POST)
    SeckillResult<Exposer> exposer(@RequestBody Long goodsId);

    @HystrixCommand(fallbackMethod = "executeFallBack")
    @RequestMapping(value = "/seckill/execution",method = RequestMethod.GET)
    SeckillResult<SeckillExecution> execute(@RequestParam("goodsId") Long goodsId,
                                            @RequestParam("userId") String userId,
                                            @RequestParam("md5") String md5);

    default PageList<SeckillGoods> pageListFallBack(){
        return new PageList<SeckillGoods>(0,null);
    }

    default SeckillGoods getDetailFallBack(){
        return new SeckillGoods();
    }

    default SeckillResult<Exposer> exposerFallback(){
        return new SeckillResult<Exposer>(false, "请求超时，已熔断");
    }

    default SeckillResult<SeckillExecution> executeFallBack(){
        return new SeckillResult<SeckillExecution>(false, "请求超时，已熔断");
    }

}
