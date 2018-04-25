package com.sukidesu.server.mapper;

import com.sukidesu.server.domain.SeckillGoods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author weixian.yan
 * @created on 19:48 2018/4/25
 * @description:
 */
@Mapper
public interface SeckillGoodsMapper {

    /**
     * 增加
     * @param goods
     * @return
     */
    int insertOne(@Param("goods") SeckillGoods goods);

    int insertBatch(@Param("goodsList") List<SeckillGoods> goodsList);

    int update(@Param("goods") SeckillGoods goods);

    List<SeckillGoods> select(@Param("domain") SeckillGoods goods);
}
