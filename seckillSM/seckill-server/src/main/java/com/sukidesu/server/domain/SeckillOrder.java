package com.sukidesu.server.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author weixian.yan
 * @created on 19:20 2018/4/25
 * @description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeckillOrder {

    /*订单id*/
    private long orderId;
    /*商品id*/
    private long goodsId;
    /*用户id*/
    private String userId;
    /*订单状态，0---失败，1---成功, 2---已发货*/
    private int orderState;
    /*创建时间*/
    private LocalDateTime createTime;
    /*更新时间*/
    private LocalDateTime updateTime;
}
