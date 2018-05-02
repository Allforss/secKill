package com.sukidesu.server.domain;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author weixian.yan
 * @created on 19:20 2018/4/25
 * @description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeckillGoods {

    /*商品id*/
    private Long goodsId;
    /*商品名*/
    private String name;
    /*库存*/
    private Long number;
    /*价格，单位：分*/
    private Long price;
    /*商品描述*/
    private String description;
    /*秒杀开始时间*/
    private Date startTime;
    /*秒杀结束时间*/
    private Date endTime;
    /*创建时间*/
    private LocalDateTime createTime;
    /*更新时间*/
    private LocalDateTime updateTime;

    //[{"goodsId":null, "name":null, "number":null, "price":null, "description":null,"startTime":null, "endTime":null, "createTime":null, "updateTime":null}]
    public static void main(String[] args) {
        SeckillGoods goods = new SeckillGoods();
        System.out.println(goods.toString());
    }

}
