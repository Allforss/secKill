package com.sukidesu.seckill.business.service;

import com.sukidesu.common.common.page.PageList;
import com.sukidesu.common.domain.SeckillGoods;
import com.sukidesu.common.dto.PageDTO;
import com.sukidesu.seckill.base.model.MessageBean;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author weixian.yan
 * @created on 16:33 2018/5/6
 * @description:  商品服务Feign接口
 */
@FeignClient(value = "seckill-server")
public interface GoodsService {

    @RequestMapping(value = "/goods/insert", method = RequestMethod.POST)
    MessageBean insertOne(@RequestBody SeckillGoods goods);

    @RequestMapping(value = "/goods/insertBatch", method = RequestMethod.POST)
    MessageBean insertBatch(@RequestBody List<SeckillGoods> goodsList);

    @RequestMapping(value = "/goods/update", method = RequestMethod.POST)
    MessageBean update(@RequestBody SeckillGoods goods);

    @RequestMapping(value = "/goods/queryOne", method = RequestMethod.POST)
    SeckillGoods queryOne(@RequestBody SeckillGoods goods);

    @RequestMapping(value = "/goods/list", method = RequestMethod.POST)
    PageList<SeckillGoods> queryList(@RequestBody PageDTO<SeckillGoods> pageDTO);

}
