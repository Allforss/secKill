package com.sukidesu.server.service.impl;

import com.sukidesu.server.domain.SeckillGoods;
import com.sukidesu.server.mapper.SeckillGoodsMapper;
import com.sukidesu.server.service.SeckillGoodsService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
    public List<SeckillGoods> selectList(int offset, int limit) {
        return goodsMapper.selectList(offset, limit);
    }

    @Override
    public int reduceNumberById(long goodsId, Date killTime) {
        return goodsMapper.reduceNumberById(goodsId, killTime);
    }
}
