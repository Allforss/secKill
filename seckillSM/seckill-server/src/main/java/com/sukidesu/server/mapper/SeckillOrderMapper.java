package com.sukidesu.server.mapper;

import com.sukidesu.common.domain.SeckillOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author weixian.yan
 * @created on 19:48 2018/4/25
 * @description:
 */
@Mapper
@Component
public interface SeckillOrderMapper {

    /**
     * 插入用户秒杀订单
     * @param order
     * @return
     */
    int insert(@Param("order") SeckillOrder order);

    /**
     * 更新订单信息
     * @param order
     * @return
     */
    int update(@Param("order") SeckillOrder order);

    /**
     * 查询订单
     * @param order
     * @return
     */
    SeckillOrder queryOrder(@Param("order") SeckillOrder order);

    /**
     * 查询订单集合
     * @param order
     * @return
     */
    List<SeckillOrder> queryList(@Param("order") SeckillOrder order);


}
