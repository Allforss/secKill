package com.sukidesu.server.service;

import com.github.pagehelper.Page;
import com.sukidesu.server.domain.SeckillGoods;

import java.util.Date;
import java.util.List;

/**
 * @author weixian.yan
 * @created on 15:54 2018/4/26
 * @description:
 */
public interface SeckillGoodsService {

    /**
     * 增加
     * @param goods
     * @return
     */
    int insertOne(SeckillGoods goods);

    /**
     * 批量插入
     * @param goodsList
     * @return
     */
    int insertBatch(List<SeckillGoods> goodsList);

    /**
     * 更新
     * @param goods
     * @return
     */
    int update(SeckillGoods goods);

    /**
     * 根据商品ID查询商品信息
     * @param goodsId
     * @return
     */
    SeckillGoods queryById(long goodsId);

    /**
     * 查询单个商品
     * @param goods
     * @return
     */
    SeckillGoods selectOne(SeckillGoods goods);

    /**
     * 分页查询商品信息
     * @return
     */
    Page<SeckillGoods> selectList(SeckillGoods goods, int offset, int limit, String orderBy);

    /**
     * 根据商品ID减少库存
     * @return
     */
    int reduceNumberById(long goodsId, Date killTime);

}
