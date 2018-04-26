package com.sukidesu.server.mapper;

import com.sukidesu.server.domain.SeckillGoods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author weixian.yan
 * @created on 19:48 2018/4/25
 * @description:
 */
@Mapper
@Component
public interface SeckillGoodsMapper {

    /**
     * 增加
     * @param goods
     * @return
     */
    int insertOne(@Param("goods") SeckillGoods goods);

    /**
     * 批量插入
     * @param goodsList
     * @return
     */
    int insertBatch(@Param("goodsList") List<SeckillGoods> goodsList);

    /**
     * 更新
     * @param goods
     * @return
     */
    int update(@Param("goods") SeckillGoods goods);

    /**
     * 根据商品ID查询商品信息
     * @param goodsId
     * @return
     */
    SeckillGoods queryById(@Param("goodsId") long goodsId);

    /**
     * 查询单个商品
     * @param goods
     * @return
     */
    SeckillGoods selectOne(@Param("goods") SeckillGoods goods);

    /**
     * 分页查询商品信息
     * @return
     */
    List<SeckillGoods> selectList(@Param("offset") int offset,@Param("limit") int limit);

    /**
     * 根据商品ID减少库存
     * @return
     */
    int reduceNumberById(@Param("goodsId") long goodsId, @Param("killTime") Date killTime);

}
