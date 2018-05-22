package com.sukidesu.seckill.business.service;

import com.sukidesu.common.domain.SeckillGoods;
import com.sukidesu.common.dto.Exposer;
import com.sukidesu.common.dto.PageDTO;
import com.sukidesu.common.dto.SeckillExecution;
import com.sukidesu.common.dto.SeckillResult;
import com.sukidesu.seckill.base.common.page.PageList;
import com.sukidesu.seckill.business.service.fallback.SeckillServiceFallBack;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author weixian.yan
 * @created on 15:16 2018/5/2
 * @description: 秒杀服务Feign接口
 */
@FeignClient(name = "seckill-server", fallback = SeckillServiceFallBack.class)
public interface SeckillService {

    @RequestMapping(value = "/seckill/list",method = RequestMethod.POST)
    PageList<SeckillGoods> pageList(@RequestBody PageDTO<SeckillGoods> pageDTO);

    @RequestMapping(value = "/seckill/detail",method = RequestMethod.GET)
    SeckillGoods getDetail(@RequestParam("goodsId") Long goodsId);

    @RequestMapping(value = "/seckill/exposer",method = RequestMethod.POST)
    SeckillResult<Exposer> exposer(@RequestBody Long goodsId);

    @RequestMapping(value = "/seckill/execution",method = RequestMethod.GET)
    SeckillResult<SeckillExecution> execute(@RequestParam("goodsId") Long goodsId,
                                            @RequestParam("userId") String userId,
                                            @RequestParam("md5") String md5);

    @RequestMapping(value = "/seckill/execution/procedure",method = RequestMethod.GET)
    SeckillResult<SeckillExecution> executeByProducer(@RequestParam("goodsId") Long goodsId,
                                            @RequestParam("userId") String userId,
                                            @RequestParam("md5") String md5);


}
