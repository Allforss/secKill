package com.sukidesu.server.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sukidesu.common.domain.SeckillGoods;
import com.sukidesu.server.mapper.SeckillGoodsMapper;
import com.sukidesu.server.service.SeckillGoodsService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author weixian.yan
 * @created on 16:07 2018/4/26
 * @description: 商品库存服务类
 */
@Log4j
@Service
public class SeckillGoodsServiceImpl implements SeckillGoodsService {

    @Autowired
    private SeckillGoodsMapper goodsMapper;

    @Override
    public int insertOne(SeckillGoods goods) {
        int res = goodsMapper.insertOne(goods);
        if(res <=0){

        }
        return 0;
    }

    @Override
    public int insertBatch(List<SeckillGoods> goodsList) {
        return goodsMapper.insertBatch(goodsList);
    }

    @Override
    public int update(SeckillGoods goods) {
        return goodsMapper.update(goods);
    }

    @Override
    public SeckillGoods queryById(long goodsId) {
        return goodsMapper.queryById(goodsId);
    }

    @Override
    public SeckillGoods selectOne(SeckillGoods goods) {
        return goodsMapper.selectOne(goods);
    }

    @Override
    public Page<SeckillGoods> selectList(SeckillGoods goods, int offset, int limit, String orderBy) {
        return PageHelper.offsetPage(offset,limit)
                .setOrderBy(orderBy)
                .doSelectPage(() -> goodsMapper.selectList(goods));
    }

    @Override
    public int reduceNumberById(long goodsId, Date killTime) {
        return goodsMapper.reduceNumberById(goodsId, killTime);
    }

    @Override
    public void killByProcedure(Map<String, Object> params) {
        goodsMapper.killByProcedure(params);
    }
}
