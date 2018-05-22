package com.sukidesu.seckill.business.service.fallback;

import com.sukidesu.common.common.enums.SeckillStateEnum;
import com.sukidesu.common.domain.SeckillGoods;
import com.sukidesu.common.dto.Exposer;
import com.sukidesu.common.dto.PageDTO;
import com.sukidesu.common.dto.SeckillExecution;
import com.sukidesu.common.dto.SeckillResult;
import com.sukidesu.seckill.base.common.page.PageList;
import com.sukidesu.seckill.business.service.SeckillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author weixian.yan
 * @created on 22:33 2018/5/19
 * @description:
 */
@Slf4j
@Component
public class SeckillServiceFallBack implements SeckillService {

    @Override
    public PageList<SeckillGoods> pageList(PageDTO<SeckillGoods> pageDTO) {
        log.info("SeckillService pageList请求超时，已熔断");
        return new PageList<SeckillGoods>(0,null);
    }

    @Override
    public SeckillGoods getDetail(Long goodsId) {
        log.info("SeckillService getDetail请求超时，已熔断");
        return null;
    }

    @Override
    public SeckillResult<Exposer> exposer(Long goodsId) {
        log.info("SeckillService exposer请求超时，已熔断");
        return new SeckillResult<Exposer>(false, "请求超时，已熔断");
    }

    @Override
    public SeckillResult<SeckillExecution> execute(Long goodsId, String userId, String md5) {
        log.info("SeckillService execute请求超时，已熔断");
        SeckillExecution execution = new SeckillExecution(goodsId, SeckillStateEnum.REQUEST_TIMEOUT);
        return new SeckillResult<SeckillExecution>(false, execution);
    }

    @Override
    public SeckillResult<SeckillExecution> executeByProducer(Long goodsId, String userId, String md5) {
        log.info("SeckillService execute请求超时，已熔断");
        SeckillExecution execution = new SeckillExecution(goodsId, SeckillStateEnum.REQUEST_TIMEOUT);
        return new SeckillResult<SeckillExecution>(false, execution);
    }
}
