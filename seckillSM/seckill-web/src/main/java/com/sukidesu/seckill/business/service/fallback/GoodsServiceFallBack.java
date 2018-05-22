package com.sukidesu.seckill.business.service.fallback;

import com.sukidesu.common.common.page.PageList;
import com.sukidesu.common.domain.SeckillGoods;
import com.sukidesu.common.dto.PageDTO;
import com.sukidesu.common.exception.BizException;
import com.sukidesu.seckill.base.model.MessageBean;
import com.sukidesu.seckill.business.service.GoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author weixian.yan
 * @created on 22:28 2018/5/19
 * @description:
 */
@Slf4j
@Component
public class GoodsServiceFallBack implements GoodsService {

    @Override
    public MessageBean insertOne(SeckillGoods goods) {
        log.info("GoodsService insertOne请求超时，已熔断");
        return new MessageBean(new BizException("请求超时"));
    }

    @Override
    public MessageBean insertBatch(List<SeckillGoods> goodsList) {
        log.info("GoodsService insertBatch请求超时，已熔断");
        return new MessageBean(new BizException("请求超时"));
    }

    @Override
    public MessageBean update(SeckillGoods goods) {
        log.info("GoodsService update请求超时，已熔断");
        return new MessageBean(new BizException("请求超时"));
    }

    @Override
    public SeckillGoods queryOne(SeckillGoods goods) {
        log.info("GoodsService queryOne请求超时，已熔断");
        return null;
    }

    @Override
    public PageList<SeckillGoods> queryList(PageDTO<SeckillGoods> pageDTO) {
        log.info("GoodsService queryList请求超时，已熔断");
        return new PageList<SeckillGoods>(0,null);
    }
}
